package org.wabc.auth.service;

import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.wabc.auth.config.SystemFeignClient;
import org.wabc.commons.model.Result;
import org.wabc.commons.model.ResultCode;
import org.wabc.commons.model.AuthUserDetails;

/**
 * 自定义用户详情服务
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final SystemFeignClient systemFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Result<String> result = systemFeignClient.getByUsername(username);

        AuthUserDetails authUserDetails = null;
        if(ResultCode.SUCCESS.code()==result.getCode()){
            authUserDetails = JSONUtil.toBean(result.getData(), AuthUserDetails.class);
        }

        if (authUserDetails == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }

        return authUserDetails;
    }
}