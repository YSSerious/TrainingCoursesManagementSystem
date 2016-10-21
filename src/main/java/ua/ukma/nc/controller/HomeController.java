package ua.ukma.nc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.ukma.nc.service.UserService;

/**
 * Created by Алексей on 15.10.2016.
 */
@Controller
public class HomeController {

    private static Logger log = LoggerFactory.getLogger(HomeController.class.getName());

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public ModelAndView getUser() {
        ModelAndView model = new ModelAndView();
        log.info("User information sent");
        model.addObject("user", userService.getById(1L).getId()+" "+userService.getById(1L).getFirstName()+" "+userService.getById(1L).getSecondName());
        model.setViewName("home");
        return model;
    }
}
