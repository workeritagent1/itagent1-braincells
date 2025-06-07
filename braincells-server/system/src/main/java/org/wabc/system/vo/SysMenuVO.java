package org.wabc.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 菜单权限表VO
 * @author wabc
 * @date 2025-05-18
 */
@Data
@Schema(description = "菜单权限表VO")
public class SysMenuVO {

    /**
     * 权限ID
     */
    @Schema(description = "权限ID")
    private Long id;

    /**
     * 权限名称:用户管理
     */
    @Schema(description = "权限名称:用户管理")
    private String name;

    /**
     * 权限标识符（如system:user:list）
     */
    @Schema(description = "权限标识符（如system:user:list）")
    private String permission;

    /**
     * 类型（menu:菜单(目录，模块), button:按钮）
     */
    @Schema(description = "类型（menu:菜单(目录，模块), button:按钮）")
    private Integer permType;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer sort;

    /**
     * 资源路径（Ant风格）/sysadmin/user/add
     */
    @Schema(description = "资源路径（Ant风格）/sysadmin/user/add")
    private String url;

    /**
     * HTTP方法（如POST|GET|PUT|DELETE）
     */
    @Schema(description = "HTTP方法（如POST|GET|PUT|DELETE）")
    private String httpMethod;

    /**
     * 父级权限ID
     */
    @Schema(description = "父级权限ID")
    private Long parentId;

    /**
     * 状态（0：禁用，1：启用）
     */
    @Schema(description = "状态（0：禁用，1：启用）")
    private Integer status;

    /**
     * 是否可见
     */
    @Schema(description = "是否可见")
    private Boolean visible;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除")
    private Boolean deleted;

    /**
     * 路由地址system,user
     */
    @Schema(description = "路由地址system,user")
    private String path;

    /**
     * 菜单图标
     */
    @Schema(description = "菜单图标")
    private String icon;

    /**
     * 组件路径
     */
    @Schema(description = "组件路径")
    private String component;

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