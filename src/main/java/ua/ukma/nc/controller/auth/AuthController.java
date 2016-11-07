package ua.ukma.nc.controller.auth;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.security.Principal;

/**
 * Created by Alex_Frankiv on 06.11.2016.
 */
@Controller
public class AuthController {

    @RequestMapping(value = "/cookie")
    public String setRoleCookie(HttpServletRequest request, HttpServletResponse response){
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String chosen = userDetails.getAuthorities().iterator().next().toString();
        if (chosen!=null){
            Cookie cookie = new Cookie("chosenRole", chosen);
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
