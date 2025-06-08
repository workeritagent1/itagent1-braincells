package org.wabc.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.wabc.auth.entity.SysUser;
import org.wabc.auth.mapper.SysRoleMapper;
import org.wabc.auth.mapper.SysUserMapper;
import org.wabc.auth.model.AuthUserDetails;

import java.util.Collections;
import java.util.List;

/**
 * 自定义用户详情服务
 */
@Service
@RequiredArgsConstructor  // 有了 @RequiredArgsConstructor，不要再手写构造器，Spring自动注入所有final字段
public class CustomUserDetailsService implements UserDetailsService {

    private final SysUserMapper sysUserMapper;
    // 注意防止循环依赖
    private final SysRoleMapper sysRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        // 1. 查询用户基本信息
        SysUser user = sysUserMapper.selectByUsername(username);
        if (user == null) {
            return null;
        }

        // 2. 查询用户角色信息（通过角色服务）
        List<String> roles = sysRoleMapper.selectRoleCodesByUserId(user.getId());

        // 3. 构建认证信息对象
        return convertToAuthInfo(user, roles);
    }

    private AuthUserDetails convertToAuthInfo(SysUser user, List<String> roles) {
        AuthUserDetails authInfo = new AuthUserDetails();
        authInfo.setId(user.getId());
        authInfo.setUsername(user.getUsername());
        authInfo.setPassword(user.getPassword());
        authInfo.setStatus(user.getStatus());
        authInfo.setAccountNonExpired(true);
        authInfo.setAccountNonLocked(true);
        authInfo.setCredentialsNonExpired(true);
        authInfo.setEnabled(true);
        authInfo.setRoles(roles != null ? roles : Collections.emptyList());
        return authInfo;
    }
}