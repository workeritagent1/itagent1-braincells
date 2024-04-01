package org.wabc.authorization.controller.result;

/**
 * 复制于：com.alibaba.nacos.api.model.v2
 *
 * @author wabc
 * @version 1.0
 * @since 2024-01-07
 */
public enum ErrorCode {
    SUCCESS(0, "success"),
    PARAMETER_MISSING(10000, "parameter missing"),
    ACCESS_DENIED(10001, "access denied"),
    DATA_ACCESS_ERROR(10002, "data access error"),
    TENANT_PARAM_ERROR(20001, "'tenant' parameter error"),
    PARAMETER_VALIDATE_ERROR(20002, "parameter validate error"),
    MEDIA_TYPE_ERROR(20003, "MediaType Error"),
    RESOURCE_NOT_FOUND(20004, "resource not found"),
    RESOURCE_CONFLICT(20005, "resource conflict"),
    CONFIG_LISTENER_IS_NULL(20006, "config listener is null"),
    CONFIG_LISTENER_ERROR(20007, "config listener error"),
    INVALID_DATA_ID(20008, "invalid dataId"),
    PARAMETER_MISMATCH(20009, "parameter mismatch"),
    SERVICE_NAME_ERROR(21000, "service name error"),
    WEIGHT_ERROR(21001, "weight error"),
    INSTANCE_METADATA_ERROR(21002, "instance metadata error"),
    INSTANCE_NOT_FOUND(21003, "instance not found"),
    INSTANCE_ERROR(21004, "instance error"),
    SERVICE_METADATA_ERROR(21005, "service metadata error"),
    SELECTOR_ERROR(21006, "selector error"),
    SERVICE_ALREADY_EXIST(21007, "service already exist"),
    SERVICE_NOT_EXIST(21008, "service not exist"),
    SERVICE_DELETE_FAILURE(21009, "service delete failure"),
    HEALTHY_PARAM_MISS(21010, "healthy param miss"),
    HEALTH_CHECK_STILL_RUNNING(21011, "health check still running"),
    ILLEGAL_NAMESPACE(22000, "illegal namespace"),
    NAMESPACE_NOT_EXIST(22001, "namespace not exist"),
    NAMESPACE_ALREADY_EXIST(22002, "namespace already exist"),
    ILLEGAL_STATE(23000, "illegal state"),
    NODE_INFO_ERROR(23001, "node info error"),
    NODE_DOWN_FAILURE(23001, "node down failure"),
    SERVER_ERROR(30000, "server error");

    private final Integer code;
    private final String msg;

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    private ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
