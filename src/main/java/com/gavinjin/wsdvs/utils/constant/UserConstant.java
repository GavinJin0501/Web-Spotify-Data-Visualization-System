package com.gavinjin.wsdvs.utils.constant;

public interface UserConstant {
    /**
     * User field restrictions
     */
    int USERNAME_LEN_LO = 4;
    int USERNAME_LEN_HI = 32;
    int PASSWORD_LEN_LO = 6;
    int PASSWORD_LEN_HI = 512;

    /**
     * DB fields names
     */
    String DB_COL_ID = "id";
    String DB_COL_USERNAME = "username";
    String DB_COL_PASSWORD = "password";

    /**
     * Session attributes
     */
    String USER_LOGIN_STATE = "user_login";
}
