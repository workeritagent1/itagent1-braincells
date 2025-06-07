package org.wabc.system.controller;

import cn.hutool.json.JSONUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wabc.commons.model.PageResult;
import org.wabc.commons.model.Result;
import org.wabc.commons.model.AuthUserDetails;
import org.wabc.system.dto.SysUserDTO;
import org.wabc.system.dto.SysUserPageDTO;
import org.wabc.system.service.SysUserService;
import org.wabc.system.vo.SysUserVO;

import javax.validation.Valid;

/**
 * 系统用户表接口控制器
 *
 * <p>负责系统用户表的开放API</p>
 * @author wabc
 * @date 2025-05-18
 */
@Tag(name = "系统用户表管理")
@RestController
@RequestMapping("/sys-users")
public class SysUserController {

    private final SysUserService sysUserService;

    @Autowired
    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Operation(summary = "创建系统用户表")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody SysUserDTO dto) {
        sysUserService.create(dto);
        return Result.success();
    }

    @Operation(summary = "更新系统用户表")
    @PutMapping("/{id}")
    public Result<Void> update(
            @Parameter(description = "主键ID") @PathVariable Long id,
            @Valid @RequestBody SysUserDTO dto) {
        sysUserService.update(id, dto);
        return Result.success();
    }

    @Operation(summary = "删除系统用户表")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@Parameter(description = "主键ID") @PathVariable Long id) {
        sysUserService.delete(id);
        return Result.success();
    }

    @Operation(summary = "分页查询系统用户表")
    @GetMapping
    public Result<PageResult<SysUserVO>> page(@Valid SysUserPageDTO pageDTO) {
        // 支持所有DTO内（含继承）的分页、排序和拓展查询参数
        return Result.success(sysUserService.page(pageDTO));
    }

    @Operation(summary = "系统用户表详情")
    @GetMapping("/{id}")
    public Result<SysUserVO> detail(@Parameter(description = "主键ID") @PathVariable Long id) {
        return Result.success(sysUserService.detail(id));
    }

    @GetMapping("/auth-user-info")
    public Result<String> getAuthUserInfo(@RequestParam("username") String username) {

        AuthUserDetails authUserInfo = sysUserService.getAuthUserInfo(username);
        // 返回string类型，防止对端服务序列化异常。
        String data = JSONUtil.toJsonStr(authUserInfo);
        return authUserInfo == null ? Result.fail("用户不存在") :  Result.success(data);

    }
}