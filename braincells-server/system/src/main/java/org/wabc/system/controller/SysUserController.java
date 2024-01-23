package org.wabc.system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wabc.system.entity.SysUser;
import org.wabc.system.result.Result;
import org.wabc.system.service.SysUserService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wabc
 * @since 2024-01-06
 */
@Controller
@RequestMapping("/sysUser")
public class SysUserController {
    @Autowired
    SysUserService sysUserService;
    @GetMapping("/loadUserByUsername/{username}")
    public Result<SysUser> loadUserByUsername(@PathVariable("username") String username) {
        SysUser sysUser = sysUserService.loadUserByUsername(username);
        return Result.success(sysUser);
    }

}

