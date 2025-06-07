

package org.wabc.system.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户与角色关联表实体类
 * @author wabc
 * @date 2025-05-18
 */
@Data
@Schema(description = "用户与角色关联表实体")
public class SysUserRole {

    /**
     * 关联的用户 ID
     */
    @Schema(description = "关联的用户 ID")
    private Long userId;
    /**
     * 关联的角色 ID
     */
    @Schema(description = "关联的角色 ID")
    private Long roleId;
}