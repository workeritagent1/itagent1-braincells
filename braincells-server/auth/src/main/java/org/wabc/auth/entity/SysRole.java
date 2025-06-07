

package org.wabc.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.wabc.commons.entity.BaseAuditEntity;

/**
 * 角色表实体类
 * @author wabc
 * @date 2025-05-18
 */
@Data
@Schema(description = "角色表实体")
public class SysRole extends BaseAuditEntity {

    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 角色名称（如管理员）
     */
    @Schema(description = "角色名称（如管理员）")
    private String name;
    /**
     * 角色编码（如ADMIN）
     */
    @Schema(description = "角色编码（如ADMIN）")
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
    private String description;
    /**
     * 状态（0：禁用，1：启用）
     */
    @Schema(description = "状态（0：禁用，1：启用）")
    private Integer status;
}