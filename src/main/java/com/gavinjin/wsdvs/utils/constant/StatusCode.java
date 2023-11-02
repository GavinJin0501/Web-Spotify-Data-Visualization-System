package com.gavinjin.wsdvs.utils.constant;

import lombok.Getter;

@Getter
public enum StatusCode {
    SUCCESS(0, "ok"),
    PARAMS_ERROR(20000, "Request parameter error"),
    OPERATION_ERROR(20100, "Operation error"),
    SYSTEM_ERROR(20500, "Server error");

    /**
     * Status code
     */
    private final int code;

    /**
     * Error message
     */
    private final String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
