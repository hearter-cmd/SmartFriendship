package com.yaonie.intelligent.assessment.server.common.model.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.yaonie.intelligent.assessment.server.common.model.constant.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-10
 */
@JsonTypeName("SecurityUser")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SecurityUser implements UserDetails, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @JsonProperty("userId")
    private Long userId;

    /**
     * 用户信息
     */
    @JsonProperty("user")
    private User user;

    /**
     * 用户名
     */
    @JsonProperty("userName")
    private String userName;

    /**
     * 权限列表
     */
    @JsonProperty("permissions")
    private Set<String> permissions;

    /**
     * 权限ID列表
     */
    @JsonProperty("permissionIds")
    private List<Long> permissionIds;

    /**
     * 角色代码列表
     */
    @JsonProperty("roleIds")
    private List<Long> roleIds;


    /**
     * 获取用户的权限集合。
     *
     * @return 权限集合
     */
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> result = new ArrayList<>();
        permissions.forEach(item -> {
            result.add(new SimpleGrantedAuthority(item));
        });
        return result;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return user.getUserPassword();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return user.getEnable().equals(CommonConstant.IS_ENABLE);
    }
}
