package org.wabc.commons.result;


import lombok.Getter;

/**
 * 通用的接口返回封装类.
 *
 * @author wabc
 * @version 1.0
 * @since 2024-06-10
 */
@Getter
public enum ResultCode {
    // 成功状态码
    SUCCESS("0", "操作成功"),

    // 用户相关错误
    USER_NOT_FOUND("10001", "用户未找到"),
    USER_LOGIN_FAILED("10002", "用户登录失败"),
    USER_ACCOUNT_LOCKED("10003", "用户账户被锁定"),
    USER_ACCOUNT_DISABLED("10004", "用户账户被禁用"),
    USERNAME_OR_PASSWORD_INCORRECT("10005", "用户名或密码错误"),
    PASSWORD_ATTEMPTS_EXCEEDED("10006", "密码尝试次数超限"),
    ACCOUNT_EXPIRED("10007", "该账号已过期"),

    // 权限和授权错误
    UNAUTHORIZED_ACCESS("20001", "未授权访问"),
    FORBIDDEN_OPERATION("20002", "禁止的操作"),

    // 参数错误
    ILLEGAL_ARGUMENT("30001", "非法参数"),
    PARAM_REQUIRED("30002", "缺少必填参数"),
    PARAM_FORMAT_ERROR("30003", "参数格式错误"),

    // 资源错误
    RESOURCE_NOT_FOUND("40001", "资源未找到"),

    // 系统错误
    INTERNAL_SERVER_ERROR("50001", "内部服务器错误"),
    SERVICE_UNAVAILABLE("50002", "服务不可用"),

    // 第三方服务错误
    THIRD_PARTY_SERVICE_FAILURE("60001", "第三方服务失败");

    private final String code;
    private final String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("{\"code\":\"%s\", \"message\":\"%s\"}", code, message);
    }

    // 根据错误码获取枚举值
    public static ResultCode fromCode(String code) {
        for (ResultCode resultCode : values()) {
            if (resultCode.getCode().equals(code)) {
                return resultCode;
            }
        }
        return INTERNAL_SERVER_ERROR; // 默认错误码
    }
}