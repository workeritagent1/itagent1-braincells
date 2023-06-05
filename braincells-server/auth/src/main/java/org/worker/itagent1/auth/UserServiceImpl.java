package org.worker.itagent1.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author itagent1.worker
 * @version 1.0
 * @ClassName UserServiceImpl 类实现Spring Security的UserDetailsService接口，用于加载用户信息；
 * @description:
 * @date 2023/6/4 10:28
 */
public class UserServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
