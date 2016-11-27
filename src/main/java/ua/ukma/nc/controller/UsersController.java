
package ua.ukma.nc.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ua.ukma.nc.entity.Role;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.service.ProjectService;
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

	int limit = 10;

	@RequestMapping(value = "/allUsers", method = RequestMethod.GET)
	public String getAllUsersPage(Model model, Principal principal,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "value", required = false) String value,
			@RequestParam(value = "type", required = false) String type) {

		List<User> users;

		if (page == null)
			page = 1;

		System.out.println(value);
		int count = userService.count();
		int noOfPages;
		if (count % limit > 0)
			noOfPages = (count / limit) + 1;
		else
			noOfPages = (count / limit);
		if (value != null && type.equals("name"))
			users = userService.getByName(value);
		else if (value != null && type.equals("role")) {
			users = userService.getAll();
			List<User> temp = new ArrayList<>();
			for (User user : users)
				for (Role role : user.getRoles())
					if (role.getTitle().equals(roleService.getByRole(value.toUpperCase()).getTitle()))
						temp.add(user);
			users.retainAll(temp);
		} else if (value != null && type.equals("project"))
			users = userService.getByName(value);
		
		else if (value != null && type.equals("group"))
			users = userService.getByName(value);
		else
			users = userService.getSome(limit, limit * (page - 1));
		model.addAttribute("users", users);
		model.addAttribute("currentPage", page);
		model.addAttribute("noOfPages", noOfPages);
		return "allUsers";
	}
}
