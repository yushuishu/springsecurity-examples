package com.shuishu.demo.security.common.config.exception;


import com.shuishu.demo.security.common.config.domain.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：谁书-ss
 * @date ：2022-12-25 11:43
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：全局异常
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    ApiResponse<String> handleException(Exception e) {
        log.info("null", e);
        e.printStackTrace();
        return ApiResponse.of(ApiResponse.Type.ERROR.value(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    ApiResponse<String> handleBusinessException(BusinessException e) {
        e.printStackTrace();
        return ApiResponse.of(e.getCode(), e.getLocalizedMessage());
    }

    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    ApiResponse<String> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        e.printStackTrace();
        return ApiResponse.of(ApiResponse.Type.ERROR.value(), e.getParameterName());
    }

    private void printParameterMap() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            Map<String, String[]> paramMap = request.getParameterMap();
            Map<String, String> newParamMap = convertRequestParamMap(paramMap);
            log.info(String.format("请求地址：%s", request.getRequestURL()));
            log.info(String.format("请求参数：%s", newParamMap));
        }
    }

    public static String errorMessage(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            for (FieldError error : list) {
                errorMessage.append(",").append(error.getField()).append(" : ").append(error.getDefaultMessage());
            }
        }
        return errorMessage.toString().replaceFirst(",", "");
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ApiResponse<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        printParameterMap();
        return ApiResponse.of(ApiResponse.Type.ERROR.value(), errorMessage(e.getBindingResult()));
    }

    @ResponseBody
    @ExceptionHandler(BindException.class)
    ApiResponse<String> handleBindException(BindException e) {
        printParameterMap();
        return ApiResponse.of(ApiResponse.Type.ERROR.value(), errorMessage(e.getBindingResult()));
    }

    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    ApiResponse<String> handleBindException(UsernameNotFoundException e) {
        printParameterMap();
        return ApiResponse.of(ApiResponse.Type.UN_AUTH.value(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    ApiResponse<String> handleBindException(BadCredentialsException e) {
        printParameterMap();
        return ApiResponse.of(ApiResponse.Type.UN_AUTH.value(), e.getMessage());
    }

    public static Map<String, String> convertRequestParamMap(Map<String, String[]> paramMap) {
        Map<String, String> newParamMap = new HashMap<>(10);
        if (paramMap != null) {
            for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
                String key = entry.getKey();
                String[] values = entry.getValue();
                newParamMap.put(key, values[0]);
            }
        }
        return newParamMap;
    }
}
