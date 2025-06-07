package org.wabc.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 系统用户表DTO
 * @author wabc
 * @date 2025-05-18
 */
@Data
@Schema(description = "系统用户表DTO")
public class SysUserDTO {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long id;

    /**
     * 用户名（唯一）
     */
    @Schema(description = "用户名（唯一）")
    @Size(min = 1, max = 255, message = "用户名（唯一）长度为1-255")
    private String username;

    /**
     * 密码（BCrypt加密）
     */
    @Schema(description = "密码（BCrypt加密）")
    @Size(min = 1, max = 255, message = "密码（BCrypt加密）长度为1-255")
    private String password;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    @Size(min = 1, max = 255, message = "昵称长度为1-255")
    private String nickname;

    /**
     * 所属部门ID
     */
    @Schema(description = "所属部门ID")
    private Long deptId;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @Size(min = 1, max = 255, message = "邮箱长度为1-255")
    private String email;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @Size(min = 1, max = 255, message = "手机号长度为1-255")
    private String phone;

    /**
     * 性别（0-未指定，1-男，2-FEMALE-女）
     */
    @Schema(description = "性别（0-未指定，1-男，2-FEMALE-女）")
    private Integer gender;

    /**
     * 状态（0-禁用，1-启用）
     */
    @Schema(description = "状态（0-禁用，1-启用）")
    private Integer status;

    /**
     * 最后登录IP
     */
    @Schema(description = "最后登录IP")
    @Size(min = 1, max = 255, message = "最后登录IP长度为1-255")
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    @Schema(description = "最后登录时间")
    private LocalDateTime lastLoginTime;

    /**
     * 删除标记（0-正常，1-删除）
     */
    @Schema(description = "删除标记（0-正常，1-删除）")
    private Integer deleted;

    /**
     * 创建者用户ID
     */
    @Schema(description = "创建者用户ID")
    private Long createdBy;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    /**
     * 最后修改者用户ID
     */
    @Schema(description = "最后修改者用户ID")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updatedTime;

}