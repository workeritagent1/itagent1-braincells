package org.wabc.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.wabc.commons.model.PageResult;
import org.wabc.system.converter.SysRoleConverter;
import org.wabc.system.dto.SysRoleDTO;
import org.wabc.system.dto.SysRolePageDTO;
import org.wabc.system.entity.SysRole;
import org.wabc.system.mapper.SysRoleMapper;
import org.wabc.system.service.SysRoleService;
import org.wabc.system.vo.SysRoleVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色表业务实现
 *
 * @author wabc
 * @date 2025-05-18
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    private final SysRoleMapper sysRoleMapper;

    public SysRoleServiceImpl(SysRoleMapper sysRoleMapper) {
        this.sysRoleMapper = sysRoleMapper;
    }

    /**
     * 新增
     * @param dto DTO对象
     */
    @Override
    public void create(SysRoleDTO dto) {
        SysRole entity = SysRoleConverter.INSTANCE.entityFromDto(dto);
        save(entity);
    }

    /**
     * 修改
     * @param id 主键
     * @param dto DTO对象
     */
    @Override
    public void update(Long id, SysRoleDTO dto) {
        SysRole entity = getById(id);
        if (entity != null) {
            SysRoleConverter.INSTANCE.updateEntityFromDto(dto, entity);
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
    public PageResult<SysRoleVO> page(SysRolePageDTO pageDTO) {
        Page<SysRole> page = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
        IPage<SysRole> pageResult = this.page(page);
        return new PageResult<>(
            pageResult.getRecords().stream().map(SysRoleConverter.INSTANCE::entityToVo).collect(Collectors.toList()),
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
    public SysRoleVO detail(Long id) {
        SysRole entity = getById(id);
        return entity != null ? SysRoleConverter.INSTANCE.entityToVo(entity) : null;
    }

    /**
     * 根据用户ID获取角色列表
     *
     * @param userId 用户ID
     * @return 角色标识列表
     */
    public List<String> getRolesByUserId(Long userId) {
        return sysRoleMapper.selectRoleCodesByUserId(userId);
    }
}