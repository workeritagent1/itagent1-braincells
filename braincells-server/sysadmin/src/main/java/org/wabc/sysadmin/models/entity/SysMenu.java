package org.wabc.system.models.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.wabc.commons.base.BaseEntity;

/**
 * <p>
 * 
 * </p>
 *
 * @author wabc
 * @since 2024-01-06
 */
@Data
public class SysMenu extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父菜单ID
     */
    private Integer parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 路由相对路径(可以表示:service微服务名称，package代码包名称，directory文件夹目录，menu菜单名称)。	为路径前后端路径：/service/package,directory/menu提供准备
     */
    private String path;

    /**
     * 组件绝对路径(vue页面完整路径，省略.vue后缀)
     */
    private String component;

    /**
     * 目录和菜单图标
     */
    private String icon;

    /**
     * 同级排序
     */
    private Integer sort;

    /**
     * 0停用，1启用
     */
    private Integer status;

    // 跳转路由绝对路径:如系统管理模块-默认跳转到用户管理菜单/sysadmin/sysUser
    private String redirect;

    /**
     * service微服务，package代码包名称，directory文件夹目录，menu菜单（package暂未用上）
     */
    private String type;
}
