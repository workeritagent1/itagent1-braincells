package ${controllerPackage};

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import org.wabc.commons.model.Result;
import org.wabc.commons.model.PageResult;
import ${servicePackage}.${entity}Service;
import ${dtoPackage}.${entity}DTO;
import ${dtoPackage}.${entity}PageDTO;
import ${voPackage}.${entity}VO;

/**
 * ${table.comment!table.name}接口控制器
 *
 * <p>负责${table.comment!table.name}的开放API</p>
 * @author ${author}
 * @date ${date}
 */
@Tag(name = "${table.comment!table.name}管理")
@RestController
@RequestMapping("/${table.name?replace('_', '-') + "s"}")
public class ${entity}Controller {

    private final ${entity}Service ${entity?uncap_first}Service;

    @Autowired
    public ${entity}Controller(${entity}Service ${entity?uncap_first}Service) {
        this.${entity?uncap_first}Service = ${entity?uncap_first}Service;
    }

    @Operation(summary = "创建${table.comment!table.name}")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody ${entity}DTO dto) {
        ${entity?uncap_first}Service.create(dto);
        return Result.success();
    }

    @Operation(summary = "更新${table.comment!table.name}")
    @PutMapping("/{id}")
    public Result<Void> update(
            @Parameter(description = "主键ID") @PathVariable Long id,
            @Valid @RequestBody ${entity}DTO dto) {
        ${entity?uncap_first}Service.update(id, dto);
        return Result.success();
    }

    @Operation(summary = "删除${table.comment!table.name}")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@Parameter(description = "主键ID") @PathVariable Long id) {
        ${entity?uncap_first}Service.delete(id);
        return Result.success();
    }

    @Operation(summary = "分页查询${table.comment!table.name}")
    @GetMapping
    public Result<PageResult<${entity}VO>> page(@Valid ${entity}PageDTO pageDTO) {
        // 支持所有DTO内（含继承）的分页、排序和拓展查询参数
        return Result.success(${entity?uncap_first}Service.page(pageDTO));
    }

    @Operation(summary = "${table.comment!table.name}详情")
    @GetMapping("/{id}")
    public Result<${entity}VO> detail(@Parameter(description = "主键ID") @PathVariable Long id) {
        return Result.success(${entity?uncap_first}Service.detail(id));
    }
}