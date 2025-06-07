package org.wabc.commons.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 认证阶段只暴露必要信息,deptId,perms不暴露
 */

/*
 * 需要标注@JsonTypeInfo否则报错：IllegalArgumentException AuthUserDetails is not in the allowlist.
 * Spring Authorization Server（或Spring Security）在从数据库反序列化授权信息（如认证token信息）时，里面保存了你的自定义类AuthUserDetails。
 但Spring Security有“允许反序列化的类型白名单（allowlist）”安全机制，你的AuthUserDetails类没有被加入白名单，所以反序列化时直接报错。
 *
 */
@Data
@Schema(description = "系统用户认证信息")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
// @JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class AuthUserDetails implements UserDetails {
    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long id;
    /**
     * 用户名（唯一）
     */
    @Schema(description = "用户名（唯一）")
    private String username;
    /**
     * 密码（BCrypt加密）
     */
    @Schema(description = "密码（BCrypt加密）")
    private String password;


    @Schema(description = "状态（0-禁用，1-启用）")
    private Integer status;

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    @Schema(description = "用户角色编码集合 [\"ROOT\",\"ADMIN\"]")
    private List<String> roles;


    //  // 手动实现 getAuthorities 方法，因为需要转换
    // 用SimpleGrantedAuthority替换Lambda  防止报异常：IllegalArgumentException: Could not resolve type
    // @JsonIgnore  防止报序列化异常；roles属性已经够用
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null) {
            return Collections.emptyList();
        }
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    // 注意：由于使用了@Data，我们不需要写getter和setter，但是需要特别注意：
    // 如果字段名是accountNonExpired，Lombok生成的setter是setAccountNonExpired，这没问题。
    // 但是，UserDetails接口中有一个方法是isAccountNonExpired，Lombok会生成一个字段的getter（对于布尔类型，是isAccountNonExpired）用于匹配接口方法。
    // 因此，我们不需要额外做任何事，Lombok会处理好。

}
