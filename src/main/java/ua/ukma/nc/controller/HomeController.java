package ua.ukma.nc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.ukma.nc.entity.ApplicationForm;
import ua.ukma.nc.entity.Project;
import ua.ukma.nc.entity.Status;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.entity.impl.real.ProjectImpl;
import ua.ukma.nc.entity.impl.real.StatusImpl;
import ua.ukma.nc.service.*;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Алексей on 15.10.2016.
 */
@Controller
public class HomeController {

    private static Logger log = LoggerFactory.getLogger(HomeController.class.getName());

    @Autowired
    private UserService userService;
    @Autowired
    private StatusLogService groupAttachmentService;


    @RequestMapping("/")

    public ModelAndView getUser() {
        ModelAndView model = new ModelAndView();
        log.info("Sending........");
        model.addObject("user", groupAttachmentService.getById(1L));
        model.setViewName("home");
        return model;
    }


}
