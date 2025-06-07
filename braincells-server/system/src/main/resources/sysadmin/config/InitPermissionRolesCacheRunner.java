package sysadmin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.wabc.sysadmin.service.SysPermissionService;


/**
 * 初始化权限角色缓存
 *
 * @author www.youlai.tech,wabc
 * @version 1.0
 * @since 2024-05-01
 */
@Component
@RequiredArgsConstructor
public class InitPermissionRolesCacheRunner implements CommandLineRunner {

    private final SysPermissionService sysPermissionService;

    @Override
    public void run(String... args) {
        sysPermissionService.refreshPermRolesRules();
    }
}