
package ua.ukma.nc.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import ua.ukma.nc.entity.User;
import ua.ukma.nc.mail.Mail;
import ua.ukma.nc.service.UserService;

/**
 * @author Oleh Khomandiak on 4 ����. 2016 �.
 */
@Controller
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private Mail mail;

    int limit = 10;

    private static Logger log = LoggerFactory.getLogger(HomeController.class.getName());

    @RequestMapping(value = "/allUsers", method = RequestMethod.GET)
    public String getAllUsers(Model model, Principal principal) {

    	int count = userService.count();

        List<User> users = userService.getSome(limit, 0);
        mail.sendMail("devcor2015@gmail.com", "xoma02@gmail.com", "a", "a");
        model.addAttribute("users", users);



        return "allUsers";
    }

}
