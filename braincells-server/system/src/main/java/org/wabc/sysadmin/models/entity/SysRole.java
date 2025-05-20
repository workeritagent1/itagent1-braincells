package org.wabc.sysadmin.models.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
public class SysRole extends BaseEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 用户状态(1:正常;0:禁用)
     */
    private Integer status;

    /**
     * 逻辑删除标识(0:未删除;1:已删除)
     */
    @TableLogic(value = "0", delval = "1")
    private Integer deleted;

    /**
     * 角色描述
     */
    private String description;
}
