package org.wabc.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author wabc
 * @since 2024-01-06
 */
@Getter
@Setter
@TableName("sys_permission")
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
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

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
