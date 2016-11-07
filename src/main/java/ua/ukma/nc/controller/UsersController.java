
package ua.ukma.nc.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ua.ukma.nc.entity.Role;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.service.RoleService;
import ua.ukma.nc.service.UserService;

/**
 * @author Oleh Khomandiak on 4 ����. 2016 �.
 */
@Controller
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    //private static Logger log = LoggerFactory.getLogger(HomeController.class.getName());

    @RequestMapping(value = "/allUsers", method = RequestMethod.GET)
    public String getAllUsers(Model model, Principal principal) {


        List<User> users = userService.getAll();
        for (User user : users) {
            if (user.getRoles().size() > 0) {
                List<Role> roles = new ArrayList<Role>();
                roles.add(roleService.getById(user.getRoles().get(0).getId()));
                if (user.getRoles().size() > 1)
                    roles.add(roleService.getById(user.getRoles().get(1).getId()));
                if (user.getRoles().size() > 2)
                    roles.add(roleService.getById(user.getRoles().get(2).getId()));
                user.setRoles(roles);
            }
        }
        //System.out.println(users.get(0).getRoles().get(0).getTitle());
        model.addAttribute("users", users);


        return "allUsers";
    }

}
