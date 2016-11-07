package ua.ukma.nc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import org.springframework.ui.Model;

import ua.ukma.nc.entity.User;
import ua.ukma.nc.service.UserService;
import java.util.ArrayList;
import java.util.List;

import ua.ukma.nc.dto.StudentProfile;
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
	
	@RequestMapping(value = "/certainUser", method = RequestMethod.GET)
	public String getCertainUser(Model model,Principal principal){
		User user=userService.getById((long) 1);
		if(user.getRoles().size()>0){
			List<Role> roles = new ArrayList<Role>();
			roles.add(roleService.getById(user.getRoles().get(0).getId()));
			if(user.getRoles().size()>1)
					roles.add(roleService.getById(user.getRoles().get(1).getId()));
			if(user.getRoles().size()>2)
				roles.add(roleService.getById(user.getRoles().get(2).getId()));
			user.setRoles(roles);}
	 
		
		model.addAttribute("user", user);
		return "certainUser";
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
