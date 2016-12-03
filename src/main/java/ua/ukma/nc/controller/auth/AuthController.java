package ua.ukma.nc.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;
import ua.ukma.nc.entity.Role;
import ua.ukma.nc.entity.SecurityUser;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.AuthProvider;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alex_Frankiv on 06.11.2016.
 */
@Controller
public class AuthController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/set_role", method = {RequestMethod.GET, RequestMethod.POST})
    public String setRole(HttpServletRequest request, Principal principal) {
        User user = userService.getByEmail(principal.getName());
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>(4);
        List<Role> availableRoles = user.getRoles();
        availableRoles.removeIf(r -> (r.getId()==4));   //removing 'STUDENT_ROLE'
        String chosenRole = request.getParameter("chosenRole");
        if (chosenRole != null) {
            for (Role role : availableRoles)              //one more validation for security reasons
                if (role.getTitle().equals(chosenRole))
                    grantedAuthorityList.add(new SimpleGrantedAuthority(role.getTitle()));
        }
        //if role not chosen or chosen incorrectly
        else {
            if (availableRoles.get(0) != null)
                grantedAuthorityList.add(new SimpleGrantedAuthority(user.getRoles().get(0).getTitle()));
        }
        //set the authentication of the current Session context
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(new SecurityUser(user.getEmail(), user.getPassword(), grantedAuthorityList, (availableRoles.size()==1)?true:false), user.getPassword(), grantedAuthorityList));
        return "redirect:/cookie";
    }

    @RequestMapping(value = "/cookie")
    public String setRoleCookie(HttpServletResponse response) {
        String chosen = SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority();
        if (chosen != null) {
            Cookie cookie = new Cookie("tcms-chosen-role", chosen);
            cookie.setMaxAge(86400);
            response.addCookie(cookie);
        }
        return "redirect:/";
    }

    //redirect already authenticated users from /login to /
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView loginHandler() {
        ModelAndView mv = new ModelAndView("login");
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken)) {
            mv.setViewName("redirect:/");
        }
        return mv;
    }

    @RequestMapping(value = "/roles_def", method = {RequestMethod.GET, RequestMethod.POST})
    public String rolesHandler(HttpServletRequest request) {
        //check if it's remember-me-token auth or not
        if(WebUtils.getCookie(request, "tcms-remember-me")!=null)
                return "redirect:/";
        return "redirect:/roles";
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public ModelAndView roles(Principal principal) {
        ModelAndView mv = new ModelAndView("chooseRole");
        User user = userService.getByEmail(principal.getName());
        List<String> available = new LinkedList<>();
        for (Role role : user.getRoles())
            if (!role.getTitle().equals("ROLE_STUDENT"))    //no students in system now
                available.add(role.getTitle());
        //no chance to choose for users with one role
        if (available.size() == 1) {
            mv = new ModelAndView("redirect:/set_role");
            mv.addObject("chosenRole", available.get(0));
            return mv;
        }
        mv.addObject("availableRoles", available);
        return mv;
    }

    @RequestMapping ("/403")
    public String error(HttpServletRequest request){
        if(request.isUserInRole("TEMP"))
            return "redirect:/roles";
        return "403";
    }
}
