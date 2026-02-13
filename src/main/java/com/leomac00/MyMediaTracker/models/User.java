package com.leomac00.MyMediaTracker.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Data
public class User implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", unique = true)
    private String userName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "password")
    private String password;

    @Column(name = "account_non_expired")
    private Boolean accountNonExpired;

    @Column(name = "account_non_locked")
    private Boolean accountNonLocked;

    @Column(name = "credentials_non_expired")
    private Boolean credentialsNonExpired;

    @Column(name = "enabled")
    private Boolean enabled;

    /** User ↔ Media join entity */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserMedia> userMedias;

    /** User ↔ Permission join entity */
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserPermission> userPermissions;

    /**
     * Helper method to expose only ACTIVE permissions
     */
    public List<Permission> getActivePermissions() {
        return userPermissions.stream()
                .filter(up -> !Boolean.TRUE.equals(up.isDeleted()))
                .map(UserPermission::getPermission)
                .toList();
    }

    /**
     * Roles as strings (useful for debugging / DTOs)
     */
    public List<String> getRoles() {
        return getActivePermissions().stream()
                .map(Permission::getDescription)
                .collect(Collectors.toList());
    }

    /**
     * Spring Security authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getActivePermissions();
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return Boolean.TRUE.equals(this.accountNonExpired);
    }

    @Override
    public boolean isAccountNonLocked() {
        return Boolean.TRUE.equals(this.accountNonLocked);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Boolean.TRUE.equals(this.credentialsNonExpired);
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(this.enabled);
    }
}
