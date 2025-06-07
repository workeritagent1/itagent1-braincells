package org.wabc.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import org.wabc.commons.model.Result;
import org.wabc.commons.model.PageResult;
import org.wabc.system.service.SysRoleService;
import org.wabc.system.dto.SysRoleDTO;
import org.wabc.system.dto.SysRolePageDTO;
import org.wabc.system.vo.SysRoleVO;

/**
 * 角色表接口控制器
 *
 * <p>负责角色表的开放API</p>
 * @author wabc
 * @date 2025-05-18
 */
@Tag(name = "角色表管理")
@RestController
@RequestMapping("/sys-roles")
public class SysRoleController {

    private final SysRoleService sysRoleService;

    @Autowired
    public SysRoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @Operation(summary = "创建角色表")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody SysRoleDTO dto) {
        sysRoleService.create(dto);
        return Result.success();
    }

    @Operation(summary = "更新角色表")
    @PutMapping("/{id}")
    public Result<Void> update(
            @Parameter(description = "主键ID") @PathVariable Long id,
            @Valid @RequestBody SysRoleDTO dto) {
        sysRoleService.update(id, dto);
        return Result.success();
    }

    @Operation(summary = "删除角色表")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@Parameter(description = "主键ID") @PathVariable Long id) {
        sysRoleService.delete(id);
        return Result.success();
    }

    @Operation(summary = "分页查询角色表")
    @GetMapping
    public Result<PageResult<SysRoleVO>> page(@Valid SysRolePageDTO pageDTO) {
        // 支持所有DTO内（含继承）的分页、排序和拓展查询参数
        return Result.success(sysRoleService.page(pageDTO));
    }

    @Operation(summary = "角色表详情")
    @GetMapping("/{id}")
    public Result<SysRoleVO> detail(@Parameter(description = "主键ID") @PathVariable Long id) {
        return Result.success(sysRoleService.detail(id));
    }
}