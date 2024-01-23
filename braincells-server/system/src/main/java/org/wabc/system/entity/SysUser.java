package org.wabc.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author wabc
 * @since 2024-01-06
 */
@Data
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别(1:男;2:女)
     */
    private Boolean gender;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 电话号码
     */
    private String telephone;

    /**
     * 电子邮箱
     */
    private String email;

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
     * 部门ID
     */
    private Integer deptId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    // 不属于数据库表的字段
    @TableField(exist = false)
    private String deptName;
    // [1,2,3]
    @TableField(exist = false)
    private List<String> roleIds;
    // [admin,video,superadmin]
    @TableField(exist = false)
    private List<String> roles;
}
