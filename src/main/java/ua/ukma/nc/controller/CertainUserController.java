package ua.ukma.nc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import org.springframework.ui.Model;

import ua.ukma.nc.entity.User;
import ua.ukma.nc.service.UserService;
import java.util.ArrayList;
import java.util.List;

import ua.ukma.nc.dto.StudentProfile;
import ua.ukma.nc.dto.UserDto;
import ua.ukma.nc.entity.Project;
import ua.ukma.nc.entity.Role;
import ua.ukma.nc.service.ProjectService;
import ua.ukma.nc.service.RoleService;
import ua.ukma.nc.service.StudentService;


@Controller 
public class CertainUserController {
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value = "/certainUser/{id}", method = RequestMethod.GET)
	public String getCertainUser(Model model,Principal principal,@PathVariable("id") Long id){
		User user=userService.getById(id);
		model.addAttribute("user", user);
		return "certainUser";
	}
	
	@RequestMapping("/users/{id}")
	public ModelAndView viewUser(@PathVariable("id") Long id) {
		ModelAndView model = new ModelAndView();
		UserDto userDto = new UserDto(userService.getById(id));
		model.addObject("user", userDto);
		model.setViewName("user");
		return model;
	}
	
	@RequestMapping("/ajaxstudentprofile")
	@ResponseBody
	public StudentProfile studentProfile(@RequestParam("student") Long studentId, @RequestParam("project") Long projectId) {
		return studentService.generateStudentProfile(studentId, projectId);
	}

	@RequestMapping("/ajaxstudentprojects")
	@ResponseBody
	public List<Project> studentProjects(@RequestParam("user") Long userId) {
		return projectService.getStudentProjects(userId);
	}
	
	@RequestMapping("/ajaxmentorprojects")
	@ResponseBody
	public List<Project> mentorProjects(@RequestParam("user") Long userId) {
		return projectService.getMentorProjects(userId);
	}
}
