package org.wabc.auth.config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.wabc.auth.admin.Constant;
import org.wabc.auth.admin.SysUser;
import org.wabc.auth.admin.SysUserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author itagent1.worker
 * @version 1.0
 * @ClassName CustomUserDetailsService
 * @description:
 * @date 2023/12/21 22:15
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    public static List<SysUserDTO> users;

    static {
        users = new ArrayList<>();
        SysUser sysUser = SysUser.builder()
                .id("1").username("workeragent1")
                .nickname("wabc")
                .gender(1)
                .avatar("a").mobile("17720231221").email("workeragent1@gmail.com").status(1).deptId("1").build();
        SysUserDTO dto = BeanUtil.toBean(sysUser, SysUserDTO.class);
        users.add(dto);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SysUserDTO> findUserList = users.stream().filter(item -> item.getUsername().equals(username)).collect(Collectors.toList());
        if (CollUtil.isEmpty(findUserList)) {
            throw new UsernameNotFoundException(Constant.USER_NOT_EXIST);
        }
        SecurityUser securityUser = new SecurityUser(findUserList.get(0));

        if (!securityUser.isEnabled()) {
            throw new DisabledException(Constant.ACCOUNT_DISABLED);
        } else if (!securityUser.isAccountNonLocked()) {
            throw new LockedException(Constant.ACCOUNT_LOCKED);
        } else if (!securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException(Constant.ACCOUNT_EXPIRED);
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(Constant.CREDENTIALS_EXPIRED);
        }
        return securityUser;
    }
}
