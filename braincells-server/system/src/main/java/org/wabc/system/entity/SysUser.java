

package org.wabc.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.wabc.commons.entity.BaseAuditEntity;

import java.time.LocalDateTime;

/**
 * 系统用户表实体类
 * @author wabc
 * @date 2025-05-18
 */
@Data
@Schema(description = "系统用户表实体")
public class SysUser extends BaseAuditEntity {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户名（唯一）
     */
    @Schema(description = "用户名（唯一）")
    private String username;
    /**
     * 密码（BCrypt加密）
     */
    @Schema(description = "密码（BCrypt加密）")
    private String password;
    /**
     * 昵称
     */
    @Schema(description = "昵称")
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
    private String email;
    /**
     * 手机号
     */
    @Schema(description = "手机号")
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
    @TableLogic
    private Integer deleted;

}