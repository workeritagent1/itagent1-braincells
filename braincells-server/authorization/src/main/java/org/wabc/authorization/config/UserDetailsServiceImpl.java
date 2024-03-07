package org.wabc.authorization.config;

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
import org.wabc.authorization.service.SystemServiceClient;

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
    SystemServiceClient userFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Result<SysUser> result = userFeignClient.loadUserByUsername(username);
        SysUser sysUser = new SysUser();
        sysUser.setUsername("admin");
        sysUser.setPassword("$2a$10$0Vb0IMD6KjNj4R71PwvzBu/QL5GjWMGZGrpF7zfJj9Y3RhvDoSqhG");
        sysUser.setStatus(1);
        SysUser user = sysUser;
//        SysUser user = result.getData();
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        MyUserDetails userDetails = new MyUserDetails(user);

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
