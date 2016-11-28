package ua.ukma.nc.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ua.ukma.nc.dto.RoleDto;
import ua.ukma.nc.dto.UserDto;
import ua.ukma.nc.exception.StatusSwitchException;
import ua.ukma.nc.service.RoleService;
import ua.ukma.nc.service.UserService;

@Controller
public class StatusSwitch {
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@ExceptionHandler(StatusSwitchException.class)
	public ModelAndView handleCustomException(StatusSwitchException ex) {

		ModelAndView model = new ModelAndView("error/statusSwitch");
		model.addObject("userId", ex.getUserId());
		model.addObject("errMsg", ex.getErrMsg());

		return model;

	}
	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
	public ModelAndView changeStatus(@PathVariable("id") Long id, @RequestParam("commentary") String commentary,
			@RequestParam("status") Long statusId) throws Exception{

		ModelAndView model = new ModelAndView();

		userService.changeStatus(id, statusId, commentary);
		model.addObject("success", "Successfully changed!");

		UserDto userDto = new UserDto(userService.getById(id));

		model.addObject("user", userDto);
		model.setViewName("user");
		
		List<RoleDto> roles = roleService.getAll().stream().map(RoleDto::new).collect(Collectors.toList());
		model.addObject("roles", roles);
		
		return model;
	}
}
