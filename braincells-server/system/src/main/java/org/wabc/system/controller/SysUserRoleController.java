package org.wabc.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import org.wabc.commons.model.Result;
import org.wabc.commons.model.PageResult;
import org.wabc.system.service.SysUserRoleService;
import org.wabc.system.dto.SysUserRoleDTO;
import org.wabc.system.dto.SysUserRolePageDTO;
import org.wabc.system.vo.SysUserRoleVO;

/**
 * 用户与角色关联表接口控制器
 *
 * <p>负责用户与角色关联表的开放API</p>
 * @author wabc
 * @date 2025-05-18
 */
@Tag(name = "用户与角色关联表管理")
@RestController
@RequestMapping("/sys-user-roles")
public class SysUserRoleController {

    private final SysUserRoleService sysUserRoleService;

    @Autowired
    public SysUserRoleController(SysUserRoleService sysUserRoleService) {
        this.sysUserRoleService = sysUserRoleService;
    }

    @Operation(summary = "创建用户与角色关联表")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody SysUserRoleDTO dto) {
        sysUserRoleService.create(dto);
        return Result.success();
    }

    @Operation(summary = "更新用户与角色关联表")
    @PutMapping("/{id}")
    public Result<Void> update(
            @Parameter(description = "主键ID") @PathVariable Long id,
            @Valid @RequestBody SysUserRoleDTO dto) {
        sysUserRoleService.update(id, dto);
        return Result.success();
    }

    @Operation(summary = "删除用户与角色关联表")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@Parameter(description = "主键ID") @PathVariable Long id) {
        sysUserRoleService.delete(id);
        return Result.success();
    }

    @Operation(summary = "分页查询用户与角色关联表")
    @GetMapping
    public Result<PageResult<SysUserRoleVO>> page(@Valid SysUserRolePageDTO pageDTO) {
        // 支持所有DTO内（含继承）的分页、排序和拓展查询参数
        return Result.success(sysUserRoleService.page(pageDTO));
    }

    @Operation(summary = "用户与角色关联表详情")
    @GetMapping("/{id}")
    public Result<SysUserRoleVO> detail(@Parameter(description = "主键ID") @PathVariable Long id) {
        return Result.success(sysUserRoleService.detail(id));
    }
}