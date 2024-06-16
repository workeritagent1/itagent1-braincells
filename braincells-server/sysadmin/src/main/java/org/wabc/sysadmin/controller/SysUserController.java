package org.wabc.system.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wabc.commons.dto.SysUserDTO;
import org.wabc.commons.result.ApiResult;
import org.wabc.system.service.SysUserService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wabc
 * @since 2024-01-06
 */
@Tag(name = "用户管理", description = "用户管理相关操作")
@RestController
@RequestMapping("/sysUser")
public class SysUserController {
    @Autowired
    SysUserService sysUserService;

    @Operation(summary = "根据用户名获取认证信息", description = "提供用于用户登录认证信息")
    @GetMapping("/username/{username}")
    public ApiResult<SysUserDTO> getByUsername(@Parameter(name = "username", description = "用户名", required = true)
                                                     @PathVariable String username) {
        SysUserDTO user = sysUserService.getAuthInfoByUsername(username);
        return ApiResult.success(user);
    }
}

