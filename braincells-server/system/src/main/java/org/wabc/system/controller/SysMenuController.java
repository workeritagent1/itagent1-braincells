package org.wabc.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import org.wabc.commons.model.Result;
import org.wabc.commons.model.PageResult;
import org.wabc.system.service.SysMenuService;
import org.wabc.system.dto.SysMenuDTO;
import org.wabc.system.dto.SysMenuPageDTO;
import org.wabc.system.vo.SysMenuVO;

/**
 * 菜单权限表接口控制器
 *
 * <p>负责菜单权限表的开放API</p>
 * @author wabc
 * @date 2025-05-18
 */
@Tag(name = "菜单权限表管理")
@RestController
@RequestMapping("/sys-menus")
public class SysMenuController {

    private final SysMenuService sysMenuService;

    @Autowired
    public SysMenuController(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    @Operation(summary = "创建菜单权限表")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody SysMenuDTO dto) {
        sysMenuService.create(dto);
        return Result.success();
    }

    @Operation(summary = "更新菜单权限表")
    @PutMapping("/{id}")
    public Result<Void> update(
            @Parameter(description = "主键ID") @PathVariable Long id,
            @Valid @RequestBody SysMenuDTO dto) {
        sysMenuService.update(id, dto);
        return Result.success();
    }

    @Operation(summary = "删除菜单权限表")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@Parameter(description = "主键ID") @PathVariable Long id) {
        sysMenuService.delete(id);
        return Result.success();
    }

    @Operation(summary = "分页查询菜单权限表")
    @GetMapping
    public Result<PageResult<SysMenuVO>> page(@Valid SysMenuPageDTO pageDTO) {
        // 支持所有DTO内（含继承）的分页、排序和拓展查询参数
        return Result.success(sysMenuService.page(pageDTO));
    }

    @Operation(summary = "菜单权限表详情")
    @GetMapping("/{id}")
    public Result<SysMenuVO> detail(@Parameter(description = "主键ID") @PathVariable Long id) {
        return Result.success(sysMenuService.detail(id));
    }
}