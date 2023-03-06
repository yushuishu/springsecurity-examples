package com.shuishu.demo.security.common.config.exception;


/**
 * @author ：谁书-ss
 * @date ：2022-12-25 11:34
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 */
public class BusinessException extends RuntimeException{
    private Object data;

    private Integer code;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Object data) {
        super(message);
        this.data = data;
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(Integer code, String message, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }

    public Integer getCode() {
        if (code == null) {
            return 500;
        }
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void throwE() {
        throw this;
    }
}
