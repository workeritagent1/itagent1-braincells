package org.wabc.system.result;


import java.io.Serializable;

/**
 * 复制于：com.alibaba.nacos.api.model.v2
 *
 * @author wabc
 * @version 1.0
 * @since 2024-01-07
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 8870230953943138110L;
    private final Integer code;
    private final String message;
    private final T data;

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result() {
        this((T) null);
    }

    public Result(T data) {
        this(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMsg(), data);
    }

    public Result(Integer code, String message) {
        this(code, message, (T) null);
    }

    public static <T> Result<T> success() {
        return new Result();
    }

    public static <T> Result<T> success(T data) {
        return new Result(data);
    }

    public static Result<String> failure(String message) {
        return failure(ErrorCode.SERVER_ERROR, message);
    }

    public static <T> Result<T> failure(ErrorCode errorCode) {
        return new Result(errorCode.getCode(), errorCode.getMsg());
    }

    public static <T> Result<T> failure(ErrorCode errorCode, T data) {
        return new Result(errorCode.getCode(), errorCode.getMsg(), data);
    }

    public String toString() {
        return "Result{errorCode=" + this.code + ", message='" + this.message + '\'' + ", data=" + this.data + '}';
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }
}
