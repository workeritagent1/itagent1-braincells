package org.wabc.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 角色表DTO
 * @author wabc
 * @date 2025-05-18
 */
@Data
@Schema(description = "角色表DTO")
public class SysRoleDTO {

    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    private Long id;

    /**
     * 角色名称（如管理员）
     */
    @Schema(description = "角色名称（如管理员）")
    @Size(min = 1, max = 255, message = "角色名称（如管理员）长度为1-255")
    private String name;

    /**
     * 角色编码（如ADMIN）
     */
    @Schema(description = "角色编码（如ADMIN）")
    @Size(min = 1, max = 255, message = "角色编码（如ADMIN）长度为1-255")
    private String code;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer sort;

    /**
     * 逻辑删除标识(0-未删除；1-已删除)
     */
    @Schema(description = "逻辑删除标识(0-未删除；1-已删除)")
    private Boolean deleted;

    /**
     * 描述
     */
    @Schema(description = "描述")
    @Size(min = 1, max = 255, message = "描述长度为1-255")
    private String description;

    /**
     * 状态（0：禁用，1：启用）
     */
    @Schema(description = "状态（0：禁用，1：启用）")
    private Integer status;

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