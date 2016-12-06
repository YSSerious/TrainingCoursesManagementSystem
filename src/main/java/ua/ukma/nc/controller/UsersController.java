
package ua.ukma.nc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.ukma.nc.dto.UserDto;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Oleh Khomandiak on 4 ����. 2016 �.
 */
@Controller
public class UsersController {

	@Autowired
	private UserService userService;

	int limit = 10;

	@RequestMapping(value = "/allUsers", method = RequestMethod.GET)
	public String getAllUsersPage(Model model, Principal principal,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "value", required = false) String[] value,
			@RequestParam(value = "type", required = false) String type) {

		List<User> users = new ArrayList<>();

		if (page == null)
			page = 1;
		int count = 0;
		int noOfPages = 0;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		boolean isMentor = authorities.contains(new SimpleGrantedAuthority("ROLE_MENTOR"));

		if (value != null && type.equals("name")) {
			String[] v = value[0].split("\\s");
			if (v.length == 1) {
				users = userService.getByName(value[0], limit, limit * (page - 1));
				count = userService.countName(value[0]);
			} else if (v.length == 2) {
				users = userService.getByName(v[0], v[1], limit, limit * (page - 1));
				count = userService.countHalfName(v[0], v[1]);
			} else if (v.length == 3) {
				users = userService.getByName(v[0], v[1], v[2], limit, limit * (page - 1));
				count = userService.countFullName(v[0], v[1], v[2]);
			}
		} else if (value != null && type.equals("role")) {
			int role1 = Integer.parseInt(value[0]);
			int role2 = 0, role3 = 0, role4 = 0;
			if(value.length>1){
				role2 = Integer.parseInt(value[1]);
				if(value.length>2){
					role3 = Integer.parseInt(value[2]);
					if(value.length>3)
						role4 = Integer.parseInt(value[3]);
				}
			}
			users = userService.getByRole(role1, role2, role3, role4);
			count = users.size();
			if (limit * page < users.size())
				users = users.subList(limit * (page - 1), limit * page);
			else
				users = users.subList(limit * (page - 1), users.size());
		} else if (value != null && type.equals("project")) {
			users = userService.studentsByProjectName(value[0], limit, limit * (page - 1));
			count = userService.countProject(value[0]);
		} else if (value != null && type.equals("group")) {
			users = userService.studentsByGroupName(value[0], limit, limit * (page - 1));
			count = userService.countGroup(value[0]);
		} else if (!isMentor) {
			users = userService.getSome(limit, limit * (page - 1));
			count = userService.count();
		}

		else {

			count = userService.count();
			users = userService.getAll();
			checkUsers(users);
			count = users.size();
			if (limit * page < users.size())
				users = users.subList(limit * (page - 1), limit * page);
			else
				users = users.subList(limit * (page - 1), users.size());
		}

		if (count % limit > 0)
			noOfPages = (count / limit) + 1;
		else
			noOfPages = (count / limit);

		model.addAttribute("users", users);
		model.addAttribute("currentPage", page);
		model.addAttribute("noOfPages", noOfPages);
		model.addAttribute("title", "All Users");
		return "allUsers";
	}

	private boolean checkUsers(List<User> users) {
		List<User> temp = new ArrayList<>();
		for (User user : users) {
			if (!userService.canView(user.getId()))
				temp.add(user);
		}
		return users.removeAll(temp);
	}

	@RequestMapping(value = "/students/inactive", method = RequestMethod.GET)
	@ResponseBody
	private List<UserDto> getInactiveStudents (){
		List<UserDto> users = new ArrayList<>();
		for(User user : userService.getInactiveStudents())
			users.add(new UserDto(user));
		return users;
	}

	@RequestMapping(value = "/mentors/free", method = RequestMethod.GET)
	@ResponseBody
	private List<UserDto> getFreeMentors (){
		List<UserDto> users = new ArrayList<>();
		for(User user : userService.getFreeMentors())
			users.add(new UserDto(user));
		return users;
	}


	/*
	 * @RequestMapping(value = "/allUsers", method = RequestMethod.POST) public
	 * String getAllUsersPagePost(Model model, Principal principal,
	 * 
	 * @RequestParam(value = "page", required = false) Integer page,
	 * 
	 * @RequestParam(value = "value", required = false) String value,
	 * 
	 * @RequestParam(value = "type", required = false) String type) {
	 * 
	 * List<User> users = new ArrayList<>();
	 * 
	 * if (page == null) page = 1; int count = 0; int noOfPages;
	 * 
	 * if (value != null && type.equals("name")) { String[] v =
	 * value.split("\\s"); if (v.length == 1) { users =
	 * userService.getByName(value, limit, limit * (page - 1)); count =
	 * userService.countName(value); } else if (v.length == 2) { users =
	 * userService.getByName(v[0], v[1], limit, limit * (page - 1)); count =
	 * userService.countHalfName(v[0], v[1]); } else if (v.length == 3) { users
	 * = userService.getByName(v[0], v[1], v[2], limit, limit * (page - 1));
	 * count = userService.countFullName(v[0], v[1], v[2]); } } else if (value
	 * != null && type.equals("role")) { users = userService.getAll();
	 * List<User> temp = new ArrayList<>(); for (User user : users) for (Role
	 * role : user.getRoles()) if
	 * (role.getTitle().equals(roleService.getByRole(value.toUpperCase()).
	 * getTitle())) temp.add(user); users.retainAll(temp); } else if (value !=
	 * null && type.equals("project")) { users =
	 * userService.studentsByProjectName(value, limit, limit * (page - 1));
	 * count = userService.countProject(value); } else if (value != null &&
	 * type.equals("group")) { users = userService.studentsByGroupName(value,
	 * limit, limit * (page - 1)); count = userService.countGroup(value); } else
	 * { users = userService.getSome(limit, limit * (page - 1)); count =
	 * userService.count(); }
	 * 
	 * if (count % limit > 0) noOfPages = (count / limit) + 1; else noOfPages =
	 * (count / limit);
	 * 
	 * model.addAttribute("users", users); model.addAttribute("currentPage",
	 * page); model.addAttribute("noOfPages", noOfPages); return "allUsers"; }
	 */
}
