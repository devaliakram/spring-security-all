package spring_security_model.security.entrysecurity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import spring_security_model.security.entity.AppUser;

import java.util.Collection;
import java.util.Collections;

/**
 * This class is loaded and understand by spring to get the information of AppUser class,
 * Simple(AppUser) is not being read by Spring Security
 * <p>
 * This class acts as an adapter:
 * <p>
 * AppUser entity
 * ↓
 * SecurityUser
 * ↓
 * UserDetails understood by Spring Security
 */
public class SecurityUser implements UserDetails {

    private final AppUser appUser;

    public SecurityUser(AppUser appUser) {
        this.appUser = appUser;
    }

    /**
     * AppUser role = ADMIN is converted into:
     * <p>
     * GrantedAuthority = ROLE_ADMIN
     *
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + appUser.getRole()));
    }

    @Override
    public String getPassword() {
        return appUser.getPassword();
    }


    @Override
    public String getUsername() {
        return appUser.getUsername();
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
        return appUser.isEnabled();
    }

}
