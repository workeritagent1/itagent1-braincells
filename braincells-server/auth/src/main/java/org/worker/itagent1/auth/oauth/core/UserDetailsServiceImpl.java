package org.worker.itagent1.auth.oauth.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.worker.itagent1.auth.admin.entity.SystemUser;
import org.worker.itagent1.auth.admin.service.SystemUserService;

/**
 * @author itagent1.worker
 * @version 1.0
 * @ClassName UserDetailsService
 * @description:
 * @date 2023/6/5 21:39
 */
public class UserDetailsServiceImpl  implements UserDetailsService {
    @Autowired
    private SystemUserService sysUserService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SystemUser systemUser = sysUserService.getUserByUsername(username);

        if (systemUser == null){
            throw new UsernameNotFoundException("用户不存在: " + username);
        }
        UserDetails userDetails = new SysUserDetails(systemUser);

        if (userDetails == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        } else if (!userDetails.isEnabled()) {
            throw new DisabledException("该账户已被禁用!");
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定!");
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期!");
        }
        return userDetails;
    }
}
