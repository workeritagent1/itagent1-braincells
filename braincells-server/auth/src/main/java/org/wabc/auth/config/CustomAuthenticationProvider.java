package org.wabc.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.wabc.auth.service.CustomUserDetailsService;

/**
 * 自定义认证Provider，用于用户名密码登录/账户状态检测
 */
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (authentication.getCredentials() != null) ? authentication.getCredentials().toString() : "";

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("用户名或密码错误");
        }
        if (!userDetails.isEnabled())               throw new DisabledException("账户被禁用");
        if (!userDetails.isAccountNonLocked())      throw new LockedException("账户被锁定");
        if (!userDetails.isAccountNonExpired())     throw new AccountExpiredException("账户已过期");
        if (!userDetails.isCredentialsNonExpired()) throw new CredentialsExpiredException("密码已过期");

        return new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}