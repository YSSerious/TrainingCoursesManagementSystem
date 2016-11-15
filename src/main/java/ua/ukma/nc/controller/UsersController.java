
package ua.ukma.nc.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ua.ukma.nc.entity.User;
import ua.ukma.nc.service.UserService;

/**
 * @author Oleh Khomandiak on 4 ����. 2016 �.
 */
@Controller
public class UsersController {

	@Autowired
	private UserService userService;

	int limit = 10;

	private static Logger log = LoggerFactory.getLogger(HomeController.class.getName());

	@RequestMapping(value = "/allUsers", method = RequestMethod.GET)
	public String getAllUsersPage(Model model, Principal principal,
			@RequestParam(value = "page", required = false) Integer page) {
		
		if (page == null)
			page = 1;
		
		int count = userService.count();
		int noOfPages;
		if (count % limit > 0)
			noOfPages = (count / limit) + 1;
		else
			noOfPages = (count / limit);
		List<User> users = userService.getSome(limit, limit * (page - 1));
		model.addAttribute("users", users);
		model.addAttribute("currentPage", page);
		model.addAttribute("noOfPages", noOfPages);
		return "allUsers";
	}
}
