package org.wabc.authorization.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.wabc.authorization.entity.SysUser;
import org.wabc.authorization.mapper.SysUserMapper;
import org.wabc.authorization.model.SysUserDetails;

import java.util.List;

/**
 * 验证用户的身份并提供相关信息给授权服务器。
 *
 * @author wabc
 * @version 1.0
 * @since 2023-12-30
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    SysUserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<SysUser> users = mapper.selectList(queryWrapper);
        SysUser sysUser = !users.isEmpty() ? users.get(0) : null;
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        SysUserDetails userDetails = new SysUserDetails(sysUser);

        if (!userDetails.isEnabled()) {
            throw new DisabledException("账户已被禁用");
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("账号已被锁定");
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("账号已过期");
        } else if (!userDetails.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException("账户的登录凭证已过期");
        }
        return userDetails;
    }
}
