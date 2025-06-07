package org.wabc.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.wabc.system.entity.SysRoleMenu;
import org.wabc.system.dto.SysRoleMenuDTO;
import org.wabc.system.dto.SysRoleMenuPageDTO;
import org.wabc.system.vo.SysRoleMenuVO;
import org.wabc.commons.model.PageResult;

/**
 * 角色与权限关联表业务接口
 *
 * <p>定义角色与权限关联表相关操作</p>
 * @author wabc
 * @date 2025-05-18
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 创建角色与权限关联表
     * @param dto {@link SysRoleMenuDTO}
     */
    void create(SysRoleMenuDTO dto);

    /**
     * 更新角色与权限关联表
     * @param id 主键
     * @param dto {@link SysRoleMenuDTO}
     */
    void update(Long id, SysRoleMenuDTO dto);

    /**
     * 删除角色与权限关联表
     * @param id 主键
     */
    void delete(Long id);

    /**
     * 分页查询
     * @param pageDTO 分页条件
     * @return 分页结果VO
     */
    PageResult<SysRoleMenuVO> page(SysRoleMenuPageDTO pageDTO);

    /**
     * 详情
     * @param id 主键
     * @return VO
     */
    SysRoleMenuVO detail(Long id);
}