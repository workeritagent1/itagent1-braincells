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


}