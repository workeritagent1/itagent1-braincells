package org.wabc.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.wabc.commons.model.PageResult;
import org.wabc.system.dto.SysRoleDTO;
import org.wabc.system.dto.SysRolePageDTO;
import org.wabc.system.entity.SysRole;
import org.wabc.system.vo.SysRoleVO;

/**
 * 角色表业务接口
 *
 * <p>定义角色表相关操作</p>
 * @author wabc
 * @date 2025-05-18
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 创建角色表
     * @param dto {@link SysRoleDTO}
     */
    void create(SysRoleDTO dto);

    /**
     * 更新角色表
     * @param id 主键
     * @param dto {@link SysRoleDTO}
     */
    void update(Long id, SysRoleDTO dto);

    /**
     * 删除角色表
     * @param id 主键
     */
    void delete(Long id);

    /**
     * 分页查询
     * @param pageDTO 分页条件
     * @return 分页结果VO
     */
    PageResult<SysRoleVO> page(SysRolePageDTO pageDTO);

    /**
     * 详情
     * @param id 主键
     * @return VO
     */
    SysRoleVO detail(Long id);

}