package ua.ukma.nc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.service.UserService;

/**
 * Created by Алексей on 15.10.2016.
 */
@Controller
public class HomeController {

    private static Logger log = LoggerFactory.getLogger(HomeController.class.getName());

    @Autowired
    private UserService userService;

    //this controller will deleted soon
    @RequestMapping("/")
    public ModelAndView getUser() {
        ModelAndView model = new ModelAndView();
        log.info("User information sent");
        model.addObject("user", userService.getByEmail("Ivanov@gmail.com"));
        model.setViewName("home");
        return model;
    }

    @ResponseBody
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public User getUser(@RequestParam Long id) {
        log.info("Sending user to frontend with id = {}", id);
        return userService.getById(id);
    }
}
