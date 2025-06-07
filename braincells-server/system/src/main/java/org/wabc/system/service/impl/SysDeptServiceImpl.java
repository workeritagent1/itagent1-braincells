package org.wabc.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.wabc.system.entity.SysDept;
import org.wabc.system.mapper.SysDeptMapper;
import org.wabc.system.service.SysDeptService;
import org.wabc.system.dto.SysDeptDTO;
import org.wabc.system.dto.SysDeptPageDTO;
import org.wabc.system.vo.SysDeptVO;
import org.wabc.system.converter.SysDeptConverter;
import org.wabc.commons.model.PageResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.stream.Collectors;

/**
 * 部门表业务实现
 *
 * @author wabc
 * @date 2025-05-18
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    /**
     * 新增
     * @param dto DTO对象
     */
    @Override
    public void create(SysDeptDTO dto) {
        SysDept entity = SysDeptConverter.INSTANCE.entityFromDto(dto);
        save(entity);
    }

    /**
     * 修改
     * @param id 主键
     * @param dto DTO对象
     */
    @Override
    public void update(Long id, SysDeptDTO dto) {
        SysDept entity = getById(id);
        if (entity != null) {
            SysDeptConverter.INSTANCE.updateEntityFromDto(dto, entity);
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
    public PageResult<SysDeptVO> page(SysDeptPageDTO pageDTO) {
        Page<SysDept> page = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
        IPage<SysDept> pageResult = this.page(page);
        return new PageResult<>(
            pageResult.getRecords().stream().map(SysDeptConverter.INSTANCE::entityToVo).collect(Collectors.toList()),
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
    public SysDeptVO detail(Long id) {
        SysDept entity = getById(id);
        return entity != null ? SysDeptConverter.INSTANCE.entityToVo(entity) : null;
    }
}