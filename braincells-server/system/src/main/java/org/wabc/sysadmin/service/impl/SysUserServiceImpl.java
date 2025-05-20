package org.wabc.sysadmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.wabc.commons.dto.SysUserDTO;
import org.wabc.sysadmin.mapper.SysUserMapper;
import org.wabc.sysadmin.models.entity.SysUser;
import org.wabc.sysadmin.service.SysUserService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wabc
 * @since 2024-01-02
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    /**
     * 根据用户名获取认证信息
     *
     * @param username
     * @return
     */
    @Override
    public SysUserDTO getAuthInfoByUsername(String username) {
        SysUserDTO user = this.baseMapper.getAuthInfoByUsername(username);
        return user;
    }


}
