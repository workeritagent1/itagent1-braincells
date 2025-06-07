package org.wabc.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.wabc.system.entity.SysMenu;
import org.wabc.system.mapper.SysMenuMapper;
import org.wabc.system.service.SysMenuService;
import org.wabc.system.dto.SysMenuDTO;
import org.wabc.system.dto.SysMenuPageDTO;
import org.wabc.system.vo.SysMenuVO;
import org.wabc.system.converter.SysMenuConverter;
import org.wabc.commons.model.PageResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.stream.Collectors;

/**
 * 菜单权限表业务实现
 *
 * @author wabc
 * @date 2025-05-18
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    /**
     * 新增
     * @param dto DTO对象
     */
    @Override
    public void create(SysMenuDTO dto) {
        SysMenu entity = SysMenuConverter.INSTANCE.entityFromDto(dto);
        save(entity);
    }

    /**
     * 修改
     * @param id 主键
     * @param dto DTO对象
     */
    @Override
    public void update(Long id, SysMenuDTO dto) {
        SysMenu entity = getById(id);
        if (entity != null) {
            SysMenuConverter.INSTANCE.updateEntityFromDto(dto, entity);
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
    public PageResult<SysMenuVO> page(SysMenuPageDTO pageDTO) {
        Page<SysMenu> page = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
        IPage<SysMenu> pageResult = this.page(page);
        return new PageResult<>(
            pageResult.getRecords().stream().map(SysMenuConverter.INSTANCE::entityToVo).collect(Collectors.toList()),
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
    public SysMenuVO detail(Long id) {
        SysMenu entity = getById(id);
        return entity != null ? SysMenuConverter.INSTANCE.entityToVo(entity) : null;
    }
}