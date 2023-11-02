package com.gavinjin.wsdvs.model.dto;

import com.gavinjin.wsdvs.utils.constant.StatusCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse<T> implements Serializable {
    private int code;
    private T data;
    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    public BaseResponse(StatusCode statusCode) {
        this(statusCode.getCode(), null, statusCode.getMessage());
    }
}
