package com.gavinjin.wsdvs.utils.exception;

import com.gavinjin.wsdvs.utils.constant.StatusCode;
import lombok.Getter;

/**
 * Customized exception during business logic
 */
@Getter
public class BusinessException extends RuntimeException {
    /**
     * error code
     */
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(StatusCode statusCode, String message) {
        super(message);
        this.code = statusCode.getCode();
    }

    public BusinessException(StatusCode statusCode) {
        this(statusCode.getCode(), statusCode.getMessage());
    }
}
