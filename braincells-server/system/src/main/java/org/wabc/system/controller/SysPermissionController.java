package org.wabc.system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wabc.system.result.Result;
import org.wabc.system.service.SysPermissionService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wabc
 * @since 2024-01-06
 */
@RestController
@RequestMapping("/sysPermission")
public class SysPermissionController {
    @Autowired
    SysPermissionService sysPermissionService;

    @GetMapping("/loadPermissionRoles_A")
    Result<String> loadPermissionRoles() {
        String permissionRoles = sysPermissionService.loadPermissionRoles();
        return Result.success(permissionRoles);
    }
}

