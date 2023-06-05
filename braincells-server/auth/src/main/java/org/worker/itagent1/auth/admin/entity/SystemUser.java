package org.worker.itagent1.auth.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 
 * </p>
 *
 * @author itagent1.worker
 * @since 2023-06-05 10:58:42
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("system_user")
@ApiModel(value = "SystemUser对象", description = "")
public class SystemUser implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户状态(1:正常;0:禁用)
     */
    private Boolean status;

    /**
     * 部门ID
     */
    private Long deptId;


    /**
     * 用户角色编码集合 ["ROOT","ADMIN"]
     */
    private List<String> roles;

    /**
     * 用户权限标识集合
     */
    private Set<String> perms;


    /**
     * 数据权限范围
     */
    private Integer dataScope;
}
