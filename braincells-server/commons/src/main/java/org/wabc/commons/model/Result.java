package org.wabc.commons.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 通用响应结果
 * 返回单个实体Result<SysUser>
 * 返回分页结果Result<PageResult<SysUser>>
 *
 * @author wabc
 * @version 1.0
 * @since 2025-05-10
 */
@Data
@Schema(description = "通用响应结果")
public class Result<T> {

    @Schema(description = "状态码，200表示成功；其他为失败或异常", example = "200", requiredMode = Schema.RequiredMode.REQUIRED)
    private int code;

    @Schema(description = "响应信息", example = "success", requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;

    @Schema(description = "返回数据内容，结构根据实际接口变化，分页时为分页对象", requiredMode = Schema.RequiredMode.NOT_REQUIRED, implementation = Object.class)
    private T data;

    // 关键：加上默认无参构造方法，否则Jackson（SpringCloud OpenFeign后端默认序列化方式）无法反序列化Result对象，
    public Result() {}

    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.code(), ResultCode.SUCCESS.message(), data);
    }

    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS.code(), ResultCode.SUCCESS.message(), null);
    }

    public static <T> Result<T> fail(String message) {
        return new Result<>(ResultCode.FAIL.code(), message, null);
    }

    public static <T> Result<T> fail(ResultCode resultCode) {
        return new Result<>(resultCode.code(), resultCode.message(), null);
    }

    public static <T> Result<T> fail(int code, String message) {
        return new Result<>(code, message, null);
    }
}