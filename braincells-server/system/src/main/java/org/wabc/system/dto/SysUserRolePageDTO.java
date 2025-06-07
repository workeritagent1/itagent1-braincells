package org.wabc.system.dto;

import org.wabc.commons.dto.PageQueryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * 用户与角色关联表分页查询DTO
 * @author wabc
 * @date 2025-05-18
 */
@Data
@Schema(description = "用户与角色关联表分页查询DTO")
public class SysUserRolePageDTO extends PageQueryDTO {

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