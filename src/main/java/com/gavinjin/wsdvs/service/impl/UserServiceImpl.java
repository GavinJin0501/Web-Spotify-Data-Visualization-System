package com.gavinjin.wsdvs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gavinjin.wsdvs.mapper.UserMapper;
import com.gavinjin.wsdvs.model.domain.User;
import com.gavinjin.wsdvs.model.vo.LoginUserVO;
import com.gavinjin.wsdvs.service.UserService;
import com.gavinjin.wsdvs.utils.StringUtils;
import com.gavinjin.wsdvs.utils.constant.StatusCode;
import com.gavinjin.wsdvs.utils.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.gavinjin.wsdvs.utils.constant.UserConstant.*;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Value("${user-service.encrypt-salt}")
    private String SALT;

    @Override
    public long userRegister(String username, String password, String checkPassword) {
        // Validation
        if (StringUtils.isAnyBlank(username, password, checkPassword)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "Parameter is empty");
        }
        if (!StringUtils.isInLengthRange(username, USERNAME_LEN_LO, USERNAME_LEN_HI)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "Username length is invalid");
        }
        if (!StringUtils.isInLengthRange(password, PASSWORD_LEN_LO) || !StringUtils.isInLengthRange(checkPassword, PASSWORD_LEN_LO)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "Password length is invalid");
        }
        if (!password.equals(checkPassword)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "Two password does not match");
        }

        // Prevent different threads trying to register with the same username
        synchronized (username.intern()) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(DB_COL_USERNAME, username);
            long count = baseMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(StatusCode.PARAMS_ERROR, "Username exists");
            }

            String encryptedPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
            User user = new User();
            user.setUsername(username);
            user.setPassword(encryptedPassword);
            boolean saveResult = save(user);
            System.out.println(user.getId());
            if (!saveResult) {
                throw new BusinessException(StatusCode.SYSTEM_ERROR, "Fail to register, database error");
            }
            return user.getId();
        }
    }

    @Override
    public LoginUserVO userLogin(String username, String password, HttpServletRequest request) {
        // Validation
        if (StringUtils.isAnyBlank(username, password)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR);
        }
        if (!StringUtils.isInLengthRange(username, USERNAME_LEN_LO, USERNAME_LEN_HI)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "Username length is invalid");
        }
        if (!StringUtils.isInLengthRange(password, PASSWORD_LEN_LO)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "Password length is invalid");
        }

        String encryptedPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DB_COL_USERNAME, username);
        queryWrapper.eq(DB_COL_PASSWORD, encryptedPassword);
        User user = baseMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "User does not exist or incorrect password");
        }

        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        return getLoginUserVO(user);
    }

    @Override
    public LoginUserVO getLoginUserVO(User user) {
        if (user == null) {
            return null;
        }

        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtil.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

    @Override
    public boolean userLogout(HttpServletRequest request) {
        if (request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
            throw new BusinessException(StatusCode.OPERATION_ERROR, "Not logged in");
        }
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }

    @Override
    public String convertUserAddressToString(String content) {
        StringBuilder sb = new StringBuilder();
        try {
            if (!content.startsWith("List(") || !content.endsWith(")")) {
                return "";
            }
            content = content.substring(5, content.length() - 1);

            String regex = "Map\\((.*?)\\)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(content);


            while (matcher.find()) {
                StringBuilder addr = new StringBuilder();
                String[] parts = matcher.group(1).split(", ");

                for (String part : parts) {
                    String[] pair = part.split(" -> ");
                    String key = pair[0], val = pair[1];
                    if (addr.length() != 0) {
                        addr.append(",");
                    }
                    addr.append(String.format("\"%s\":\"%s\"", key, val));
                }

                if (sb.length() != 0) {
                    sb.append(",");
                }
                sb.append(String.format("{%s}", addr));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.format("[%s]", sb);
    }

    @Override
    public User getLoggedInUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (user == null || user.getId() == null) {
            throw new BusinessException(StatusCode.NOT_LOGIN_ERROR);
        }

        long userId = user.getId();
        user = getById(userId);
        if (user == null) {
            throw new BusinessException(StatusCode.NOT_LOGIN_ERROR);
        }

        return user;
    }
}
