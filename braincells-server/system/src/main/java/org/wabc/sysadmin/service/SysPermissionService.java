package org.wabc.sysadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.wabc.sysadmin.models.entity.SysPermission;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wabc
 * @since 2024-01-06
 */
public interface SysPermissionService extends IService<SysPermission> {

    /**
     * 刷新Redis缓存中角色菜单的权限规则，角色和菜单信息变更调用
     */
    boolean refreshPermRolesRules();

    String loadPermissionRoles();
}
