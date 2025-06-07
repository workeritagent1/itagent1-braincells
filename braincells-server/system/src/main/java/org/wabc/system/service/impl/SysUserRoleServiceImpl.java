package org.wabc.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.wabc.system.entity.SysUserRole;
import org.wabc.system.mapper.SysUserRoleMapper;
import org.wabc.system.service.SysUserRoleService;
import org.wabc.system.dto.SysUserRoleDTO;
import org.wabc.system.dto.SysUserRolePageDTO;
import org.wabc.system.vo.SysUserRoleVO;
import org.wabc.system.converter.SysUserRoleConverter;
import org.wabc.commons.model.PageResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.stream.Collectors;

/**
 * 用户与角色关联表业务实现
 *
 * @author wabc
 * @date 2025-05-18
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    /**
     * 新增
     * @param dto DTO对象
     */
    @Override
    public void create(SysUserRoleDTO dto) {
        SysUserRole entity = SysUserRoleConverter.INSTANCE.entityFromDto(dto);
        save(entity);
    }

    /**
     * 修改
     * @param id 主键
     * @param dto DTO对象
     */
    @Override
    public void update(Long id, SysUserRoleDTO dto) {
        SysUserRole entity = getById(id);
        if (entity != null) {
            SysUserRoleConverter.INSTANCE.updateEntityFromDto(dto, entity);
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
    public PageResult<SysUserRoleVO> page(SysUserRolePageDTO pageDTO) {
        Page<SysUserRole> page = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
        IPage<SysUserRole> pageResult = this.page(page);
        return new PageResult<>(
            pageResult.getRecords().stream().map(SysUserRoleConverter.INSTANCE::entityToVo).collect(Collectors.toList()),
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
    public SysUserRoleVO detail(Long id) {
        SysUserRole entity = getById(id);
        return entity != null ? SysUserRoleConverter.INSTANCE.entityToVo(entity) : null;
    }
}