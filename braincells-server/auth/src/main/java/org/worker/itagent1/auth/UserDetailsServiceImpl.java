//package org.worker.itagent1.auth;
//
//import lombok.AllArgsConstructor;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.LockedException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.worker.itagent1.auth.api.UserFeignClient;
//import org.worker.itagent1.auth.result.Result;
//import org.worker.itagent1.auth.result.ResultCode;
//
///**
// * @author itagent1.worker
// * @version 1.0
// * @ClassName UserDetailsServiceImpl
// * @description: 客户端信息
// * @date 2023/5/25 22:23
// */
//@Service
//@AllArgsConstructor
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    private UserFeignClient userFeignClient;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        String clientId = JwtUtils.getAuthClientId();
//        OAuthClientEnum client = OAuthClientEnum.getByClientId(clientId);
//
//        Result result;
//        OAuthUserDetails oauthUserDetails = null;
//        switch (client) {
//            default:
//                result = userFeignClient.getUserByUsername(username);
//                if (ResultCode.SUCCESS.getCode().equals(result.getCode())) {
//                    SysUser sysUser = (SysUser)result.getData();
//                    oauthUserDetails = new OAuthUserDetails(sysUser);
//                }
//                break;
//        }
//        if (oauthUserDetails == null || oauthUserDetails.getId() == null) {
//            throw new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.getMsg());
//        } else if (!oauthUserDetails.isEnabled()) {
//            throw new DisabledException("该账户已被禁用!");
//        } else if (!oauthUserDetails.isAccountNonLocked()) {
//            throw new LockedException("该账号已被锁定!");
//        } else if (!oauthUserDetails.isAccountNonExpired()) {
//            throw new AccountExpiredException("该账号已过期!");
//        }
//        return oauthUserDetails;
//    }
//}