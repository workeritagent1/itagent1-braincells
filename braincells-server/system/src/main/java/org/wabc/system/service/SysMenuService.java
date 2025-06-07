package org.wabc.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.wabc.system.entity.SysMenu;
import org.wabc.system.dto.SysMenuDTO;
import org.wabc.system.dto.SysMenuPageDTO;
import org.wabc.system.vo.SysMenuVO;
import org.wabc.commons.model.PageResult;

/**
 * 菜单权限表业务接口
 *
 * <p>定义菜单权限表相关操作</p>
 * @author wabc
 * @date 2025-05-18
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 创建菜单权限表
     * @param dto {@link SysMenuDTO}
     */
    void create(SysMenuDTO dto);

    /**
     * 更新菜单权限表
     * @param id 主键
     * @param dto {@link SysMenuDTO}
     */
    void update(Long id, SysMenuDTO dto);

    /**
     * 删除菜单权限表
     * @param id 主键
     */
    void delete(Long id);

    /**
     * 分页查询
     * @param pageDTO 分页条件
     * @return 分页结果VO
     */
    PageResult<SysMenuVO> page(SysMenuPageDTO pageDTO);

    /**
     * 详情
     * @param id 主键
     * @return VO
     */
    SysMenuVO detail(Long id);
}