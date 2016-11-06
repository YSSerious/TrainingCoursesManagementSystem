package ua.ukma.nc.controller.auth;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Alex_Frankiv on 06.11.2016.
 */
@Controller
public class CookieSetter {

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
}
