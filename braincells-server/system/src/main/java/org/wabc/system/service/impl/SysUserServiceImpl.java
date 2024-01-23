package org.wabc.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wabc.system.entity.SysUser;
import org.wabc.system.mapper.SysUserMapper;
import org.wabc.system.service.SysUserService;

import java.util.List;

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
    @Autowired
    SysUserMapper mapper;
    @Override
    public SysUser loadUserByUsername(String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<SysUser> clients = mapper.selectList(queryWrapper);
        return !clients.isEmpty() ? clients.get(0) : null;
    }
}
