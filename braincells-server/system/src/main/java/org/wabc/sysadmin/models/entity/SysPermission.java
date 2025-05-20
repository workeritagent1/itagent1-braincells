package org.wabc.sysadmin.models.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;
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
@Accessors(chain = true)
public class SysPermission extends BaseEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限所属菜单
     */
    private Integer menuId;

    /**
     * URL权限标识POST|GET|PUT|DELETE:/{service}/{menu}/{btn}
     */
    private String urlPerm;

    /**
     * 按钮权限标识{directory}:{menu}:{btn}
     */
    private String btnPerm;

}
