package org.wabc.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.wabc.system.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户与角色关联表 Mapper接口
 * <p>对数据库表sys_user_role的CURD操作</p>
 * @author wabc
 * @date 2025-05-18
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
}