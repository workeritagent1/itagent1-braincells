package org.wabc.sysadmin.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

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
@AllArgsConstructor
public class SysUserRole {

    private Long userId;

    private Integer roleId;

}

