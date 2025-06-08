package org.wabc.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.wabc.commons.model.PageResult;
import org.wabc.system.converter.SysUserConverter;
import org.wabc.system.dto.SysUserDTO;
import org.wabc.system.dto.SysUserPageDTO;
import org.wabc.system.entity.SysUser;
import org.wabc.system.mapper.SysUserMapper;
import org.wabc.system.service.SysRoleService;
import org.wabc.system.service.SysUserService;
import org.wabc.system.vo.SysUserVO;

import java.util.stream.Collectors;
/**
 * 系统用户表业务实现
 *
 * @author wabc
 * @date 2025-05-18
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper sysUserMapper;

    // 注意防止循环依赖
    private final SysRoleService sysRoleService;

    public SysUserServiceImpl(
            SysUserMapper sysUserMapper,
            SysRoleService sysRoleService
    ) {
        this.sysUserMapper = sysUserMapper;
        this.sysRoleService = sysRoleService;
    }

    /**
     * 新增
     * @param dto DTO对象
     */
    @Override
    public void create(SysUserDTO dto) {
        SysUser entity = SysUserConverter.INSTANCE.entityFromDto(dto);
        save(entity);
    }

    /**
     * 修改
     * @param id 主键
     * @param dto DTO对象
     */
    @Override
    public void update(Long id, SysUserDTO dto) {
        SysUser entity = getById(id);
        if (entity != null) {
            SysUserConverter.INSTANCE.updateEntityFromDto(dto, entity);
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
    public PageResult<SysUserVO> page(SysUserPageDTO pageDTO) {
        Page<SysUser> page = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
        IPage<SysUser> pageResult = this.page(page);
        return new PageResult<>(
            pageResult.getRecords().stream().map(SysUserConverter.INSTANCE::entityToVo).collect(Collectors.toList()),
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
    public SysUserVO detail(Long id) {
        SysUser entity = getById(id);
        return entity != null ? SysUserConverter.INSTANCE.entityToVo(entity) : null;
    }
}