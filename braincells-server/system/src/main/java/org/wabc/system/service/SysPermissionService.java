package org.wabc.system.service;

import org.wabc.system.entity.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wabc
 * @since 2024-01-06
 */
public interface SysPermissionService extends IService<SysPermission> {
    String loadPermissionRoles();
}
