package com.gavinjin.wsdvs.config;

import com.gavinjin.wsdvs.model.dto.BaseResponse;
import com.gavinjin.wsdvs.utils.ResponseUtils;
import com.gavinjin.wsdvs.utils.constant.StatusCode;
import com.gavinjin.wsdvs.utils.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({Exception.class})
    public BaseResponse<?> businessExceptionHandler(Exception e) {
        try {
            BusinessException be = (BusinessException) e;
            log.error("BusinessException", be);
            return ResponseUtils.error(be.getCode(), be.getMessage());
        } catch (Exception ee) {
            log.error("Exception", e);
            return ResponseUtils.error(StatusCode.SYSTEM_ERROR.getCode(), e.getMessage());
        }
    }
}
