package com.vijay.travel.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Data
@Setter
@Accessors(chain = true)
public class User implements UserDetails {

    @Id
    private String username;

    private String password;

    @OneToMany(fetch = FetchType.EAGER)
    private List<String> roles;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Destination> destinations;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (roles == null) {
            throw new RuntimeException("User roles are null. Cannot create List of GrantedAuthority");
        }

        return roles.stream().map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
