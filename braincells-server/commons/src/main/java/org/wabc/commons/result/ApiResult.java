package org.wabc.commons.result;

import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 通用的接口返回封装类.
 *
 * @author wabc
 * @version 1.0
 * @since 2024-05-22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResult<T> implements Serializable {
    private int status;
    private String message;
    private T data;

    public ApiResult(HttpStatus httpStatus, T data) {
        this.status = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
        this.data = data;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }

    // 401 Unauthorized
    public static <T> ApiResult<T> unauthorized() {
        return new ApiResult<>(HttpStatus.UNAUTHORIZED, null);
    }

    // 403 Forbidden
    public static <T> ApiResult<T> forbidden() {
        return new ApiResult<>(HttpStatus.FORBIDDEN, null);
    }

    // 404 Not Found
    public static <T> ApiResult<T> notFound() {
        return new ApiResult<>(HttpStatus.NOT_FOUND, null);
    }

    // 429 Too Many Requests
    public static <T> ApiResult<T> tooManyRequests() {
        return new ApiResult<>(HttpStatus.TOO_MANY_REQUESTS, null);
    }

    // 500 Internal Server Error
    public static <T> ApiResult<T> internalServerError() {
        return new ApiResult<>(HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    // 503 Service Unavailable
    public static <T> ApiResult<T> serviceUnavailable() {
        return new ApiResult<>(HttpStatus.SERVICE_UNAVAILABLE, null);
    }

    public static <T> ApiResult<T> success() {
        return success(null);
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(HttpStatus.OK, data);
    }

    public static <T> ApiResult<T> failed() {
        return failed(null);
    }

    public static <T> ApiResult<T> failed(T data) {
        return new ApiResult<>(HttpStatus.INTERNAL_SERVER_ERROR, data);
    }

    public static boolean isSuccess(ApiResult<?> result) {
        return result != null && HttpStatus.OK.value() == result.getStatus();
    }
}
