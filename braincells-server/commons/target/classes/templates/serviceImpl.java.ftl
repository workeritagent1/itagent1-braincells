package ${serviceImplPackage};

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import ${entityPackage}.${entity};
import ${mapperPackage}.${entity}Mapper;
import ${servicePackage}.${entity}Service;
import ${dtoPackage}.${entity}DTO;
import ${dtoPackage}.${entity}PageDTO;
import ${voPackage}.${entity}VO;
import ${converterPackage}.${entity}Converter;
import org.wabc.commons.model.PageResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.stream.Collectors;

/**
 * ${table.comment!table.name}业务实现
 *
 * @author ${author}
 * @date ${date}
 */
@Service
public class ${entity}ServiceImpl extends ServiceImpl<${entity}Mapper, ${entity}> implements ${entity}Service {

    /**
     * 新增
     * @param dto DTO对象
     */
    @Override
    public void create(${entity}DTO dto) {
        ${entity} entity = ${entity}Converter.INSTANCE.entityFromDto(dto);
        save(entity);
    }

    /**
     * 修改
     * @param id 主键
     * @param dto DTO对象
     */
    @Override
    public void update(Long id, ${entity}DTO dto) {
        ${entity} entity = getById(id);
        if (entity != null) {
            ${entity}Converter.INSTANCE.updateEntityFromDto(dto, entity);
            updateById(entity);
        }
    }

    /**
     * 删除
     * @param id 主键
     */
    @Override
    public void delete(Long id) {
        removeById(id);
    }

    /**
     * 分页
     * @param pageDTO 查询DTO
     * @return PageResult
     */
    @Override
    public PageResult<${entity}VO> page(${entity}PageDTO pageDTO) {
        Page<${entity}> page = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
        IPage<${entity}> pageResult = this.page(page);
        return new PageResult<>(
            pageResult.getRecords().stream().map(${entity}Converter.INSTANCE::entityToVo).collect(Collectors.toList()),
            pageResult.getCurrent(),
            pageResult.getSize(),
            pageResult.getTotal()
        );
    }

    /**
     * 详情
     * @param id 主键
     * @return VO
     */
    @Override
    public ${entity}VO detail(Long id) {
        ${entity} entity = getById(id);
        return entity != null ? ${entity}Converter.INSTANCE.entityToVo(entity) : null;
    }
}