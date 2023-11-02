package com.gavinjin.wsdvs.config;

import com.gavinjin.wsdvs.model.dto.BaseResponse;
import com.gavinjin.wsdvs.utils.ResponseUtils;
import com.gavinjin.wsdvs.utils.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException", e);
        return ResponseUtils.error(e.getCode(), e.getMessage());
    }
}
