package org.wabc.commons.dto;

import lombok.Data;

import java.util.List;

/**
 * Class description.
 *  用户信息
 * @author wabc
 * @version 1.0
 * @since 2024-05-27
 */
@Data
public class SysUserDTO {
    /**
     * 用户ID
     */
    private Long id;

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
    private Integer status;

    /**
     * 用户角色编码集合 ["ROOT","ADMIN"]
     */
    private List<String> roles;

    /**
     * 部门ID
     */
    private Long deptId;
}
