package org.wabc.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import org.wabc.commons.model.Result;
import org.wabc.commons.model.PageResult;
import org.wabc.system.service.SysRoleMenuService;
import org.wabc.system.dto.SysRoleMenuDTO;
import org.wabc.system.dto.SysRoleMenuPageDTO;
import org.wabc.system.vo.SysRoleMenuVO;

/**
 * 角色与权限关联表接口控制器
 *
 * <p>负责角色与权限关联表的开放API</p>
 * @author wabc
 * @date 2025-05-18
 */
@Tag(name = "角色与权限关联表管理")
@RestController
@RequestMapping("/sys-role-menus")
public class SysRoleMenuController {

    private final SysRoleMenuService sysRoleMenuService;

    @Autowired
    public SysRoleMenuController(SysRoleMenuService sysRoleMenuService) {
        this.sysRoleMenuService = sysRoleMenuService;
    }

    @Operation(summary = "创建角色与权限关联表")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody SysRoleMenuDTO dto) {
        sysRoleMenuService.create(dto);
        return Result.success();
    }

    @Operation(summary = "更新角色与权限关联表")
    @PutMapping("/{id}")
    public Result<Void> update(
            @Parameter(description = "主键ID") @PathVariable Long id,
            @Valid @RequestBody SysRoleMenuDTO dto) {
        sysRoleMenuService.update(id, dto);
        return Result.success();
    }

    @Operation(summary = "删除角色与权限关联表")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@Parameter(description = "主键ID") @PathVariable Long id) {
        sysRoleMenuService.delete(id);
        return Result.success();
    }

    @Operation(summary = "分页查询角色与权限关联表")
    @GetMapping
    public Result<PageResult<SysRoleMenuVO>> page(@Valid SysRoleMenuPageDTO pageDTO) {
        // 支持所有DTO内（含继承）的分页、排序和拓展查询参数
        return Result.success(sysRoleMenuService.page(pageDTO));
    }

    @Operation(summary = "角色与权限关联表详情")
    @GetMapping("/{id}")
    public Result<SysRoleMenuVO> detail(@Parameter(description = "主键ID") @PathVariable Long id) {
        return Result.success(sysRoleMenuService.detail(id));
    }
}