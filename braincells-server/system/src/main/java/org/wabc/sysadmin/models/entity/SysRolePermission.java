package org.wabc.sysadmin.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author wabc
 * @since 2024-01-06
 */
@Data
@AllArgsConstructor
public class SysRolePermission {
    private Integer roleId;
    private Integer permissionId;
}
