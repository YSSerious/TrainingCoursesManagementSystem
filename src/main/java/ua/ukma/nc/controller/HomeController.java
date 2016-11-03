package ua.ukma.nc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.ukma.nc.entity.Status;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.entity.impl.real.StatusImpl;
import ua.ukma.nc.service.StatusService;
import ua.ukma.nc.service.UserService;

/**
 * Created by Алексей on 15.10.2016.
 */
@Controller
public class HomeController {

    private static Logger log = LoggerFactory.getLogger(HomeController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private StatusService statusService;


    @RequestMapping("/")
    public ModelAndView getUser() {
        ModelAndView model = new ModelAndView();
        log.info("Sending........");
        model.addObject("user",userService.getById(41L).getRoles().get(0));
        model.setViewName("home");
        return model;
    }


}
