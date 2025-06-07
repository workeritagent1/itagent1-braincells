package org.wabc.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 部门表VO
 * @author wabc
 * @date 2025-05-18
 */
@Data
@Schema(description = "部门表VO")
public class SysDeptVO {

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
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