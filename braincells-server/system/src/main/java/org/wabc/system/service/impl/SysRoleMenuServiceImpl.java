package org.wabc.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.wabc.system.entity.SysRoleMenu;
import org.wabc.system.mapper.SysRoleMenuMapper;
import org.wabc.system.service.SysRoleMenuService;
import org.wabc.system.dto.SysRoleMenuDTO;
import org.wabc.system.dto.SysRoleMenuPageDTO;
import org.wabc.system.vo.SysRoleMenuVO;
import org.wabc.system.converter.SysRoleMenuConverter;
import org.wabc.commons.model.PageResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.stream.Collectors;

/**
 * 角色与权限关联表业务实现
 *
 * @author wabc
 * @date 2025-05-18
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    /**
     * 新增
     * @param dto DTO对象
     */
    @Override
    public void create(SysRoleMenuDTO dto) {
        SysRoleMenu entity = SysRoleMenuConverter.INSTANCE.entityFromDto(dto);
        save(entity);
    }

    /**
     * 修改
     * @param id 主键
     * @param dto DTO对象
     */
    @Override
    public void update(Long id, SysRoleMenuDTO dto) {
        SysRoleMenu entity = getById(id);
        if (entity != null) {
            SysRoleMenuConverter.INSTANCE.updateEntityFromDto(dto, entity);
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
    public PageResult<SysRoleMenuVO> page(SysRoleMenuPageDTO pageDTO) {
        Page<SysRoleMenu> page = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
        IPage<SysRoleMenu> pageResult = this.page(page);
        return new PageResult<>(
            pageResult.getRecords().stream().map(SysRoleMenuConverter.INSTANCE::entityToVo).collect(Collectors.toList()),
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
    public SysRoleMenuVO detail(Long id) {
        SysRoleMenu entity = getById(id);
        return entity != null ? SysRoleMenuConverter.INSTANCE.entityToVo(entity) : null;
    }
}