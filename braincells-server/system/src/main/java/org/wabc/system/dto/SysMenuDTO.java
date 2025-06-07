package org.wabc.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 菜单权限表DTO
 * @author wabc
 * @date 2025-05-18
 */
@Data
@Schema(description = "菜单权限表DTO")
public class SysMenuDTO {

    /**
     * 权限ID
     */
    @Schema(description = "权限ID")
    private Long id;

    /**
     * 权限名称:用户管理
     */
    @Schema(description = "权限名称:用户管理")
    @Size(min = 1, max = 255, message = "权限名称:用户管理长度为1-255")
    private String name;

    /**
     * 权限标识符（如system:user:list）
     */
    @Schema(description = "权限标识符（如system:user:list）")
    @Size(min = 1, max = 255, message = "权限标识符（如system:user:list）长度为1-255")
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
    @Size(min = 1, max = 255, message = "资源路径（Ant风格）/sysadmin/user/add长度为1-255")
    private String url;

    /**
     * HTTP方法（如POST|GET|PUT|DELETE）
     */
    @Schema(description = "HTTP方法（如POST|GET|PUT|DELETE）")
    @Size(min = 1, max = 255, message = "HTTP方法（如POST|GET|PUT|DELETE）长度为1-255")
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
    @Size(min = 1, max = 255, message = "路由地址system,user长度为1-255")
    private String path;

    /**
     * 菜单图标
     */
    @Schema(description = "菜单图标")
    @Size(min = 1, max = 255, message = "菜单图标长度为1-255")
    private String icon;

    /**
     * 组件路径
     */
    @Schema(description = "组件路径")
    @Size(min = 1, max = 255, message = "组件路径长度为1-255")
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