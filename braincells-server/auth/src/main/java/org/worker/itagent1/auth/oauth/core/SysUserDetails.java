package org.worker.itagent1.auth.oauth.core;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.worker.itagent1.auth.admin.entity.SystemUser;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author itagent1.worker
 * @version 1.0
 * @ClassName SysUserDetails
 * @description: 系统用户认证信息
 * @date 2023/6/5 23:16
 */
@Data
public class SysUserDetails implements UserDetails {

    /**
     * 扩展字段：用户ID
     */
    private Long userId;

    /**
     * 扩展字段：部门ID
     */
    private Long deptId;

    /**
     * 用户角色数据权限集合
     */
    private Integer dataScope;

    private Set<String> perms;


    /**
     * 默认字段
     */
    private String username;
    private String password;
    private Boolean enabled;
    private Collection<SimpleGrantedAuthority> authorities;


    public SysUserDetails(SystemUser user) {
        this.setUserId(user.getUserId());
        this.setDeptId(user.getDeptId());
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        this.setEnabled(user.getStatus());

        if (CollectionUtil.isNotEmpty(user.getRoles())) {
            authorities = user.getRoles().stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }
        this.setPerms(user.getPerms());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
        return false;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
