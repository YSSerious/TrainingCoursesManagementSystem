package ua.ukma.nc.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ua.ukma.nc.entity.Role;
import ua.ukma.nc.entity.SecurityUser;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.entity.impl.real.RoleImpl;
import ua.ukma.nc.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
        User user = userService.getByEmail(email);
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String role = "ROLE_TEMP";
        //trying to assign role from cookie (if it's remember-me logging in)
        for (Cookie cookie : request.getCookies())
            if (cookie.getName().equals("tcms-chosen-role"))
                role = cookie.getValue();
        grantedAuthorityList.add(new SimpleGrantedAuthority(role));
        //no students allowed in system; fail student-only authentication
        List<Role> availableRoles = user.getRoles();
        availableRoles.removeIf(r -> (r.getId()==4));
        if(availableRoles.isEmpty())
            return null;
        return new SecurityUser(email, user.getPassword(), grantedAuthorityList, (availableRoles.size()==1)?true:false);
    }
}
