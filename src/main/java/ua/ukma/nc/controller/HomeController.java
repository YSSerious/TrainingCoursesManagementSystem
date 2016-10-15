package ua.ukma.nc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Алексей on 15.10.2016.
 */
@Controller
public class HomeController {

    private static Logger log = LoggerFactory.getLogger(HomeController.class.getName());

//    @RequestMapping("/")
//    public ModelAndView getUser() {
//        ModelAndView model = new ModelAndView();
//        model.addObject("greeting", "PRIVET!");
//        model.setViewName("home");
//        return model;
//    }
}
