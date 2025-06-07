

package org.wabc.system.entity;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import org.wabc.commons.entity.BaseAuditEntity;

/**
 * 部门表实体类
 * @author wabc
 * @date 2025-05-18
 */
@Data
@Schema(description = "部门表实体")
public class SysDept extends BaseAuditEntity {

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String name;
    /**
     * 部门代码，用于系统内部识别
     */
    @Schema(description = "部门代码，用于系统内部识别")
    private String code;
    /**
     * 父部门 ID，用于构建部门层级关系
     */
    @Schema(description = "父部门 ID，用于构建部门层级关系")
    private Long parentId;
    /**
     * 排序值
     */
    @Schema(description = "排序值")
    private Integer sort;
    /**
     * 状态（0-禁用，1-启用）
     */
    @Schema(description = "状态（0-禁用，1-启用）")
    private Integer status;
    /**
     * 描述
     */
    @Schema(description = "描述")
    private String description;
}