package org.wabc.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.wabc.system.entity.SysUser;

/**
 * 系统用户表 Mapper接口
 * <p>对数据库表sys_user的CURD操作</p>
 * @author wabc
 * @date 2025-05-18
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据用户名查询用户信息
     * 属性没有： account_non_expired, account_non_locked,credentials_non_expired, enabled,
     * @param username 用户名
     * @return 用户实体
     */
    @Select("SELECT " +
            "id, username, password, nickname, email, phone, status " +
            "FROM sys_user " +
            "WHERE username = #{username} " +
            "AND deleted = 0")
    SysUser selectByUsername(@Param("username") String username);

}