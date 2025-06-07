package org.wabc.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.wabc.system.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色与权限关联表 Mapper接口
 * <p>对数据库表sys_role_menu的CURD操作</p>
 * @author wabc
 * @date 2025-05-18
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
}