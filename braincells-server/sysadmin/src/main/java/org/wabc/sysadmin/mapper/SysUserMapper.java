package org.wabc.sysadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.wabc.commons.dto.SysUserDTO;
import org.wabc.sysadmin.models.entity.SysUser;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wabc
 * @since 2024-01-06
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 根据用户名获取认证信息
     *
     * @param username
     * @return
     */
    SysUserDTO getAuthInfoByUsername(String username);

}
