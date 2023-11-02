package com.gavinjin.wsdvs.controller;

import com.gavinjin.wsdvs.model.dto.BaseResponse;
import com.gavinjin.wsdvs.model.dto.user.UserLoginRequest;
import com.gavinjin.wsdvs.model.dto.user.UserRegisterRequest;
import com.gavinjin.wsdvs.model.vo.LoginUserVO;
import com.gavinjin.wsdvs.service.UserService;
import com.gavinjin.wsdvs.utils.ResponseUtils;
import com.gavinjin.wsdvs.utils.StringUtils;
import com.gavinjin.wsdvs.utils.constant.StatusCode;
import com.gavinjin.wsdvs.utils.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest request) {
        if (request == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "Empty http request");
        }

        String username = request.getUsername();
        String password = request.getPassword();
        String checkPassword =request.getCheckPassword();
        if (StringUtils.isAnyBlank(username, password, checkPassword)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "Parameter is empty");
        }

        long result = userService.userRegister(username, password, checkPassword);
        return ResponseUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "Empty http request");
        }

        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();
        if (StringUtils.isAnyBlank(username, password)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR);
        }

        LoginUserVO loginUserVO = userService.userLogin(username, password, request);
        return ResponseUtils.success(loginUserVO);
    }

    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "Empty http request");
        }
        boolean result = userService.userLogout(request);
        return ResponseUtils.success(result);
    }
}
