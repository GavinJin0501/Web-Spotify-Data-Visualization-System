package com.gavinjin.wsdvs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gavinjin.wsdvs.model.domain.User;
import com.gavinjin.wsdvs.model.vo.LoginUserVO;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends IService<User> {
    /**
     * User register
     * @param username
     * @param password
     * @param checkPassword
     * @return
     */
    long userRegister(String username, String password, String checkPassword);

    /**
     * User login
     * @param username
     * @param password
     * @param request
     * @return
     */
    LoginUserVO userLogin(String username, String password, HttpServletRequest request);

    /**
     * Get logged in user vo
     * @param user
     * @return
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * Log out
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);

    String convertUserAddressToString(String fileName);

    User getLoggedInUser(HttpServletRequest request);
}
