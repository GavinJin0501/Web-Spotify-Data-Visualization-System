package com.gavinjin.wsdvs.utils;

import com.gavinjin.wsdvs.model.dto.BaseResponse;
import com.gavinjin.wsdvs.utils.constant.StatusCode;

/**
 * Utils to generate the corresponding response objects
 */
public class ResponseUtils {
    public static <T>BaseResponse<T> success(T data) {
        return new BaseResponse<>(StatusCode.SUCCESS.getCode(), data, StatusCode.SUCCESS.getMessage());
    }

    public static <T>BaseResponse<T> error(int code, String message) {
        return new BaseResponse<>(code, null, message);
    }

    // public static <T>BaseResponse<T> error(StatusCode statusCode) {
    //     return new BaseResponse<>(statusCode.getCode(), null, statusCode.getMessage());
    // }
}
