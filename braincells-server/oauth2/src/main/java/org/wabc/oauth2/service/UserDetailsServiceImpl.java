package org.wabc.oauth2.service;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.wabc.oauth2.model.SysUserDetails;
import org.wabc.commons.dto.SysUserDTO;
import org.wabc.commons.result.ApiResult;
import org.wabc.commons.result.ResultCode;

/**
 * 验证用户的身份并提供相关信息给授权服务器。
 *
 * @author wabc
 * @version 1.0
 * @since 2023-12-30
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserDetailsFeignClient userDetailsFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApiResult<SysUserDTO> result = userDetailsFeignClient.getByUsername(username);
        log.info("[用户信息]-{}",JSONUtil.toJsonPrettyStr(result.getData()));
        SysUserDetails userDetails= null;
        if (ApiResult.isSuccess(result) && result.getData() != null) {
            userDetails = new SysUserDetails(result.getData());
        }

        if (userDetails == null) {
            throw new UsernameNotFoundException(ResultCode.USERNAME_OR_PASSWORD_INCORRECT.getMessage());
        } else if (!userDetails.isEnabled()) {
            throw new DisabledException(ResultCode.USER_ACCOUNT_DISABLED.getMessage());
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException(ResultCode.USER_ACCOUNT_LOCKED.getMessage());
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException(ResultCode.ACCOUNT_EXPIRED.getMessage());
        }
        return userDetails;
    }
}
