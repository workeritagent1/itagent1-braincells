package org.wabc.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.wabc.system.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单权限表 Mapper接口
 * <p>对数据库表sys_menu的CURD操作</p>
 * @author wabc
 * @date 2025-05-18
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
}