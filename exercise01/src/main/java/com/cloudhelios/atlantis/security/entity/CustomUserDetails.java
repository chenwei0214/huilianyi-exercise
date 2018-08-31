package com.cloudhelios.atlantis.security.entity;

import com.cloudhelios.atlantis.domain.AtlUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * author: chenwei
 * createDate: 18-8-29 下午5:43
 * description:
 */
public class CustomUserDetails implements UserDetails {

    private String username;

    private String password;

    private AtlUser atlUser;

    private final boolean enabled;
    private final boolean accountNonExpired;
    private final boolean credentialsNonExpired;
    private final boolean accountNonLocked;
    private final Set<GrantedAuthority> authorities;

    public CustomUserDetails(AtlUser atlUser, boolean enabled, boolean accountNonExpired,
                             boolean credentialsNonExpired, boolean accountNonLocked,
                             Set<GrantedAuthority> authorities) {
        this.atlUser = atlUser;
        if (atlUser != null
                && !StringUtils.isEmpty(atlUser.getEmployeeId())
                && !StringUtils.isEmpty(atlUser.getPassword())) {
            setUsername(atlUser.getUsername());
            setPassword(atlUser.getPassword());
            this.enabled = enabled;
            this.accountNonExpired = accountNonExpired;
            this.credentialsNonExpired = credentialsNonExpired;
            this.accountNonLocked = accountNonLocked;
            this.authorities = new HashSet<>();
        } else {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AtlUser getAtlUser() {
        return atlUser;
    }

    public void setAtlUser(AtlUser atlUser) {
        this.atlUser = atlUser;
    }
}
