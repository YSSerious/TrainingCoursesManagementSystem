package ua.ukma.nc.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.ukma.nc.entity.Role;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex_Frankiv on 06.11.2016.
 */
@Controller
public class AuthController {

    @Autowired
    UserService userService;

    @RequestMapping (value = "/set_role")
    public String setRole(HttpServletRequest request, Principal principal){
        User user = userService.getByEmail(principal.getName());
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
        String chosenRole = request.getParameter("chosenRole");
        if(chosenRole==null)
            for(Cookie cookie : request.getCookies())
                if(cookie.getName().equals("tcms-chosen-role"))
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
        //set the authentication of the current Session context
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal.getName(), user.getPassword(), grantedAuthorityList));
        return "redirect:/";
    }

    @RequestMapping(value = "/cookie")
    public String setRoleCookie(HttpServletResponse response){
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String chosen = userDetails.getAuthorities().iterator().next().toString();
        if (chosen!=null){
            Cookie cookie = new Cookie("tcms-chosen-role", chosen);
            cookie.setMaxAge(86400);
            response.addCookie(cookie);
        }
        return "redirect:/";
    }

    //redirect already authenticated users from /login to /
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView loginHandler(){
        ModelAndView mv = new ModelAndView("login");
        if(SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated()&&
                !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken) ){
            mv.setViewName("redirect:/");
        }
        return mv;
    }
}
