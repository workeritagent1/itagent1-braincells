package org.wabc.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.wabc.system.entity.SysRole;

import java.util.List;

/**
 * 角色表 Mapper接口
 * <p>对数据库表sys_role的CURD操作</p>
 * @author wabc
 * @date 2025-05-18
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 根据用户ID查询角色标识列表
     *
     * @param userId 用户ID
     * @return 角色标识列表
     */
    @Select("SELECT r.code " +
            "FROM sys_role r " +
            "INNER JOIN sys_user_role ur ON r.id = ur.role_id " +
            "WHERE ur.user_id = #{userId} " +
            "AND r.deleted = 0")
    List<String> selectRoleCodesByUserId(@Param("userId") Long userId);
}