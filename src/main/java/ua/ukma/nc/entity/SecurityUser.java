package ua.ukma.nc.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by Alex_Frankiv on 03.12.2016.
 */
public class SecurityUser extends org.springframework.security.core.userdetails.User {

    private boolean monorole;

    public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities, boolean hasOneRole) {
        super(username, password, authorities);
        this.monorole =hasOneRole;
    }

    public SecurityUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public boolean getMonorole() {
        return monorole;
    }

    public void setMonorole(boolean monorole) {
        this.monorole = monorole;
    }
}
