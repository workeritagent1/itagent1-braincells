package org.wabc.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import org.wabc.commons.model.Result;
import org.wabc.commons.model.PageResult;
import org.wabc.system.service.SysDeptService;
import org.wabc.system.dto.SysDeptDTO;
import org.wabc.system.dto.SysDeptPageDTO;
import org.wabc.system.vo.SysDeptVO;

/**
 * 部门表接口控制器
 *
 * <p>负责部门表的开放API</p>
 * @author wabc
 * @date 2025-05-18
 */
@Tag(name = "部门表管理")
@RestController
@RequestMapping("/sys-depts")
public class SysDeptController {

    private final SysDeptService sysDeptService;

    @Autowired
    public SysDeptController(SysDeptService sysDeptService) {
        this.sysDeptService = sysDeptService;
    }

    @Operation(summary = "创建部门表")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody SysDeptDTO dto) {
        sysDeptService.create(dto);
        return Result.success();
    }

    @Operation(summary = "更新部门表")
    @PutMapping("/{id}")
    public Result<Void> update(
            @Parameter(description = "主键ID") @PathVariable Long id,
            @Valid @RequestBody SysDeptDTO dto) {
        sysDeptService.update(id, dto);
        return Result.success();
    }

    @Operation(summary = "删除部门表")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@Parameter(description = "主键ID") @PathVariable Long id) {
        sysDeptService.delete(id);
        return Result.success();
    }

    @Operation(summary = "分页查询部门表")
    @GetMapping
    public Result<PageResult<SysDeptVO>> page(@Valid SysDeptPageDTO pageDTO) {
        // 支持所有DTO内（含继承）的分页、排序和拓展查询参数
        return Result.success(sysDeptService.page(pageDTO));
    }

    @Operation(summary = "部门表详情")
    @GetMapping("/{id}")
    public Result<SysDeptVO> detail(@Parameter(description = "主键ID") @PathVariable Long id) {
        return Result.success(sysDeptService.detail(id));
    }
}