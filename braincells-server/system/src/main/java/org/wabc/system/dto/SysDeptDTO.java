package org.wabc.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 部门表DTO
 * @author wabc
 * @date 2025-05-18
 */
@Data
@Schema(description = "部门表DTO")
public class SysDeptDTO {

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    private Long id;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    @Size(min = 1, max = 255, message = "部门名称长度为1-255")
    private String name;

    /**
     * 部门代码，用于系统内部识别
     */
    @Schema(description = "部门代码，用于系统内部识别")
    @Size(min = 1, max = 255, message = "部门代码，用于系统内部识别长度为1-255")
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
    @Size(min = 1, max = 255, message = "描述长度为1-255")
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