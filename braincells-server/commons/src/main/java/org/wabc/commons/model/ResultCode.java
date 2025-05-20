package org.wabc.commons.model;


/**
 * 响应码常量类（枚举实现）. 简洁之美
 *
 * @author wabc
 * @version 1.0
 * @since 2025-05-10
 */
public enum ResultCode {
    SUCCESS(200, "操作成功"),
    FAIL(400, "请求失败"),
    UNAUTHORIZED(401, "未认证"),
    FORBIDDEN(403, "无权限"),
    NOT_FOUND(404, "资源不存在"),
    SERVER_ERROR(500, "服务器内部错误"),
    // 可扩展更多业务码
    ;

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}