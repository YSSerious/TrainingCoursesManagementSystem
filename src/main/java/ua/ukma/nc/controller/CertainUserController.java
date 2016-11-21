package ua.ukma.nc.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import org.springframework.ui.Model;

import ua.ukma.nc.dto.*;
import ua.ukma.nc.entity.*;
import ua.ukma.nc.entity.impl.real.FinalReviewImpl;
import ua.ukma.nc.service.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CertainUserController {
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private FinalReviewService finalReviewService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private FinalReviewCriterionService finalReviewCriterionService;

	@Autowired
	private CriterionService criterionService;

	@RequestMapping(value = "/certainUser/{id}", method = RequestMethod.GET)
	public String getCertainUser(Model model, Principal principal, @PathVariable("id") Long id) {
		User user = userService.getById(id);
		model.addAttribute("user", user);
		return "certainUser";
	}
	
	@RequestMapping(value = "/manageRoles", method = RequestMethod.POST)
	public ModelAndView changeRole(@RequestParam("student") Long student, @RequestParam("roles") Long[] chroles){
		User user = userService.getById(student);
		userService.deleteRoles(user);
		
		for(Long roleId: chroles)
			userService.addRole(user, roleService.getById(roleId));
		
		return new ModelAndView("redirect:" + "users/"+student);
	}
	

	@RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
	public ModelAndView changeStatus(@PathVariable("id") Long id, @RequestParam("commentary") String commentary,
			@RequestParam("status") Long statusId) {

		ModelAndView model = new ModelAndView();
		
		try {
			userService.changeStatus(id, statusId, commentary);
			model.addObject("success", "Successfully changed!");
		} catch (IllegalArgumentException e) {
			model.addObject("error", e.getMessage());
		} catch (Exception e) {
			model.addObject("error", "Check your permission and student information!");
		}

		UserDto userDto = new UserDto(userService.getById(id));

		model.addObject("user", userDto);
		model.setViewName("user");
		
		List<RoleDto> roles = roleService.getAll().stream().map(RoleDto::new).collect(Collectors.toList());
		model.addObject("roles", roles);
		
		return model;
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ModelAndView viewUser(@PathVariable("id") Long id) {
		ModelAndView model = new ModelAndView();
		UserDto userDto = new UserDto(userService.getById(id));
		model.addObject("user", userDto);
		model.setViewName("user");

		List<RoleDto> roles = roleService.getAll().stream().map(RoleDto::new).collect(Collectors.toList());
		model.addObject("roles", roles);
		
		return model;
	}

	@RequestMapping("/ajaxstudentprofile")
	@ResponseBody
	public StudentProfile studentProfile(@RequestParam("student") Long studentId,
			@RequestParam("project") Long projectId) {
		if (userService.canView(studentId))
			return studentService.generateStudentProfile(studentId, projectId);
		
		return null;
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

	@RequestMapping (value = "/ajax/get/final_review_form")
	@ResponseBody
	public List<FinalReviewCriterion> getFinReviewFormData(@RequestParam("user") Long userId){
		List<FinalReviewCriterion> result = null;
		List<Project> all = projectService.getStudentProjects(userId);
		//only for testing gonna be fixed ASAP
		all.stream().forEach(p->System.out.println(p));
		Project current = all.get(0);
		if(current!=null){
			if(finalReviewService.exists(userId, groupService.getByUserProject(userId, current.getId()).getId(), "F")) {
				result = finalReviewCriterionService.getByFinalReview(finalReviewService.getByStudent(current.getId(), userId, "F").getId());
			}
			else{
				result = new LinkedList<FinalReviewCriterion>();
				for (Criterion criterion : criterionService.getByProject(current.getId())){
					FinalReviewCriterion frc = new FinalReviewCriterion();
					frc.setCriterion(criterion);
					result.add(frc);
				}
			}
		}
		return result;
	}

	@RequestMapping(value = "/ajax/post/final_review_form/{id}", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public String postFinRev(Principal principal, @PathVariable("id") Long userId, @RequestBody JsonWrapperFinRev data){
		User mentor = userService.getByEmail(principal.getName());
		List<Project> all = projectService.getStudentProjects(userId);
		//only for testing gonna be fixed ASAP
		all.stream().forEach(p->System.out.println(p));
		Project current = all.get(0);
		if(current!=null) {
			FinalReview review = null;
			if(finalReviewService.exists(userId, groupService.getByUserProject(userId, current.getId()).getId(), "F")){
				review = finalReviewService.getByStudent(current.getId(), userId, "F");
				for(FinalReviewCriterion criterion : finalReviewCriterionService.getByFinalReview(review.getId())){
					finalReviewCriterionService.deleteFinalReviewCriterion(criterion);
				}
				if (data.getComment() != null)
					review.setCommentary(data.getComment());
				review.setEmployee(mentor);
				finalReviewService.updateFinalReview(review);
			}
			else {
				review = new FinalReviewImpl((long) 0, new Timestamp(System.currentTimeMillis()), userService.getById(userId), mentor, "F", current, "");
				if (data.getComment() != null)
					review.setCommentary(data.getComment());
				finalReviewService.createFinalReview(review);
				review = finalReviewService.getByStudent(current.getId(), userId, "F");
			}
			for(FinalReviewCriterion frc : data.getData()){
				frc.setFinalReview(review);
				finalReviewCriterionService.createFinalReviewCriterion(frc);
			}
			return "true";
		}
		else
			return "false";
	}
}
