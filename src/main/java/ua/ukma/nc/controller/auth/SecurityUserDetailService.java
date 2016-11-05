package ua.ukma.nc.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.ukma.nc.entity.Role;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex_Frankiv on 05.11.2016.
 */
@Service
public class SecurityUserDetailService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getByEmail(email);
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();

        for(Role role : user.getRoles())
            grantedAuthorityList.add(new SimpleGrantedAuthority(role.getTitle()));

        return new org.springframework.security.core.userdetails.User(email, user.getPassword(), grantedAuthorityList);
    }
}
