package org.wabc.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.wabc.system.entity.SysUserRole;
import org.wabc.system.dto.SysUserRoleDTO;
import org.wabc.system.dto.SysUserRolePageDTO;
import org.wabc.system.vo.SysUserRoleVO;
import org.wabc.commons.model.PageResult;

/**
 * 用户与角色关联表业务接口
 *
 * <p>定义用户与角色关联表相关操作</p>
 * @author wabc
 * @date 2025-05-18
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 创建用户与角色关联表
     * @param dto {@link SysUserRoleDTO}
     */
    void create(SysUserRoleDTO dto);

    /**
     * 更新用户与角色关联表
     * @param id 主键
     * @param dto {@link SysUserRoleDTO}
     */
    void update(Long id, SysUserRoleDTO dto);

    /**
     * 删除用户与角色关联表
     * @param id 主键
     */
    void delete(Long id);

    /**
     * 分页查询
     * @param pageDTO 分页条件
     * @return 分页结果VO
     */
    PageResult<SysUserRoleVO> page(SysUserRolePageDTO pageDTO);

    /**
     * 详情
     * @param id 主键
     * @return VO
     */
    SysUserRoleVO detail(Long id);
}