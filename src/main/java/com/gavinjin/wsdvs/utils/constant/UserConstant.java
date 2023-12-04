package com.gavinjin.wsdvs.utils.constant;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

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
     * DB table prefix
     */
    String DB_TABLE_PLAYLISTS = "playlists_";
    String DB_TABLE_HISTORY = "history_";


    /**
     * Session attributes
     */
    String USER_LOGIN_STATE = "user_login";

    /**
     * Date formatter
     */
    SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    // DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}
