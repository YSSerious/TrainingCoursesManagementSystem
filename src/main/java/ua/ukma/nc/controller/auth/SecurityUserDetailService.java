package ua.ukma.nc.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ua.ukma.nc.entity.Role;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Alex_Frankiv on 05.11.2016.
 */
@Service
public class SecurityUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        User user = userService.getByEmail(email);
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();

        String chosenRole = request.getParameter("chosenRole");
        if(chosenRole==null)
            for(Cookie cookie : request.getCookies())
                if(cookie.getName().equals("chosenRole"))
                    chosenRole = cookie.getValue();
        if(chosenRole!=null){
            for(Role role : user.getRoles())
                if(role.getTitle().equals(chosenRole))
                    grantedAuthorityList.add(new SimpleGrantedAuthority(role.getTitle()));
        }
        //if role not chosen or chosen incorrectly
        else {
            if(user.getRoles().get(0)!=null)
                grantedAuthorityList.add(new SimpleGrantedAuthority(user.getRoles().get(0).getTitle()));
        }

        return new org.springframework.security.core.userdetails.User(email, user.getPassword(), grantedAuthorityList);
    }
}
