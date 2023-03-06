package com.shuishu.demo.security.common.config.domain;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author ：谁书-ss
 * @date ：2022-12-24 19:51
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -657368404509719862L;

    private int code;

    private String message;

    private T data;

    public ApiResponse() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ApiResponse(Type type, String message) {
        this.code = type.value;
        this.message = message;
    }

    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResponse(T data, int code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public ApiResponse(Type type, String message, T data) {
        this.code = type.value;
        this.message = message;
        if (data != null) {
            this.data = data;
        }

    }

    public static <T> ApiResponse<T> instance(int code, String message) {
        return new ApiResponse<T>(code, message);
    }

    public static <T> ApiResponse<T> instance(T data) {
        return new ApiResponse<T>(data, Type.SUCCESS.value(), "操作成功");
    }

    public static <T> ApiResponse<T> instance(T data, int code, String message) {
        return new ApiResponse<T>(data, code, message);
    }

    public static <T> ApiResponse<T> of(int code, String message) {
        return new ApiResponse<T>(code, message);
    }

    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<T>(data, Type.SUCCESS.value(), "操作成功");
    }

    public static <T> ApiResponse<T> of(T data, int code, String message) {
        return new ApiResponse<T>(data, code, message);
    }


    /**
     * 静态方法要使用泛型参数的话，要声明其为泛型方法
     *
     * @param <T> T
     * @return T
     */
    public static <T> ApiResponse<T> success() {
        return success("操作成功");
    }

    public static <T> ApiResponse<T> success(T data) {
        return success("操作成功", data);
    }

    public static <T> ApiResponse<T> success(String message) {
        return success(message, (T) null);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<T>(Type.SUCCESS, message, data);
    }

    public static <T> ApiResponse<T> unAuth() {
        return new ApiResponse<T>(Type.UN_AUTH, "未登陆", (T) null);
    }

    public static <T> ApiResponse<T> warn(String message) {
        return warn(message, (T) null);
    }

    public static <T> ApiResponse<T> warn(String message, T data) {
        return new ApiResponse<T>(Type.WARN, message, data);
    }

    public static <T> ApiResponse<T> forbidden() {
        return new ApiResponse<T>(Type.FORBIDDEN, "禁止访问", (T) null);
    }

    public static <T> ApiResponse<T> timeOut() {
        return new ApiResponse<T>(Type.TIME_OUT, "请求超时", (T) null);
    }

    public static <T> ApiResponse<T> bodyLarge() {
        return new ApiResponse<T>(Type.BODY_LARGE, "请求实体过大", (T) null);
    }

    public static <T> ApiResponse<T> error() {
        return error("操作失败");
    }

    public static <T> ApiResponse<T> error(String message) {
        return error(message, (T) null);
    }

    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<T>(Type.ERROR, message, data);
    }

    public static <T> ApiResponse<T> serviceUnavailable() {
        return new ApiResponse<T>(Type.SERVICE_UNAVAILABLE, "服务不可用（超载或停机维护中）", (T) null);
    }


    public static enum Type {
        /**
         * 常用响应码
         */
        SUCCESS(200),
        //未授权 （登录认证失败）
        UN_AUTH(401),
        //警告
        WARN(402),
        //拒绝请求，禁止访问 （无权限）
        FORBIDDEN(403),
        //请求超时
        TIME_OUT(408),
        //请求实体过大
        BODY_LARGE(413),
        //服务器内部错误
        ERROR(500),
        //服务不可用(超载或停机维护)
        SERVICE_UNAVAILABLE(503),
        ;

        private final int value;

        private Type(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
