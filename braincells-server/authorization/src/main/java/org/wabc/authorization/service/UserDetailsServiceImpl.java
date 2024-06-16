package org.wabc.commons.authorization.service;

import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.wabc.commons.authorization.entity.SysUser;
import org.wabc.commons.authorization.mapper.SysUserMapper;
import org.wabc.commons.authorization.model.SysUserDetails;
import org.wabc.commons.authorization.result.Result;
import org.wabc.commons.authorization.result.ResultCode;

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
    UserFeignClient userFeignClient;

    @Autowired
    SysUserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserDetails userDetails = null;
        Result<SysUser> result = userFeignClient.getUserByUsername(username);
        if (Result.isSuccess(result)) {
            SysUser user = result.getData();
            if (null != user) {
                userDetails = new SysUserDetails(user);
            }
        }
        System.out.println(JSONUtil.toJsonPrettyStr(result.getData()));

        if (userDetails == null) {
            throw new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.getMsg());
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
