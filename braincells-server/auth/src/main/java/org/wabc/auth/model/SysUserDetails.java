package org.wabc.auth.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.wabc.commons.dto.SysUserDTO;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 系统用户详细信息，仅用于实例化UserDetails类型，传递User转到UserDetails的字段信息
 *
 * @author wabc
 * @version 1.0
 * @since 2023-12-31
 */
@Data
public class SysUserDetails implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private Boolean enabled;
    private String deptId;
    // 权限数据
    private Collection<SimpleGrantedAuthority> authorities;

    public SysUserDetails() {
    }

    public SysUserDetails(SysUserDTO user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        // 1 启用，0 禁用
        this.setEnabled(user.getStatus() == 1);
        //  [admin,video,superadmin]
        if (user.getRoles() != null) {
            authorities = new ArrayList<>();
            user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
