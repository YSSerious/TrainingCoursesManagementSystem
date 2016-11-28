package ua.ukma.nc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.ukma.nc.dto.*;
import ua.ukma.nc.entity.*;
import ua.ukma.nc.entity.impl.real.FinalReviewImpl;
import ua.ukma.nc.exception.StatusSwitchException;
import ua.ukma.nc.service.*;

import java.security.Principal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CertainUserController {
	
	@Autowired
	private CategoryService categoryService;
	
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
	public ModelAndView changeRole(@RequestParam("student") Long student, @RequestParam("roles") List<Long> chRoles){
		User user = userService.getById(student);
		
		List<Role> roles = user.getRoles();
		List<Long> rolesId = new ArrayList<Long>();
		
		for(Role role: roles)
			rolesId.add(role.getId());
		
		for(Long roleId: rolesId)
			if(!chRoles.contains(roleId))
				userService.deleteRole(user, roleId);
		
		for(Long roleId: chRoles)
			if(!rolesId.contains(roleId))
				userService.addRole(user, roleService.getById(roleId));
		
		return new ModelAndView("redirect:" + "users/"+student);
	}
	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ModelAndView viewUser(@PathVariable("id") Long id) {
		ModelAndView model = new ModelAndView();
		
		User user = userService.getById(id);
		UserDto userDto = new UserDto(user);
		
		model.addObject("user", userDto);
		model.setViewName("user");
		model.addObject("title", userDto.getLastName()+" "+userDto.getFirstName());

		List<Role> allRoles = roleService.getAll();
		List<Role> userRoles = user.getRoles();
		
		List<RoleDto> rolesDto = new ArrayList<RoleDto>();
		
		for(Role role: allRoles){
			RoleDto roleDto = new RoleDto(role);
			
			for(Role userRole: userRoles)
				if(role.getId() == userRole.getId()){
					roleDto.setHave(true);
					roleDto.setActive(userService.isUsingRole(id, role.getId()));
					break;
				}
			
			rolesDto.add(roleDto);
		}
		
		model.addObject("roles", rolesDto);
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
	public List<ProjectDto> studentProjects(@RequestParam("user") Long userId) {
		return projectService.getStudentProjects(userId).stream().map(ProjectDto::new).collect(Collectors.toList());
	}

	@RequestMapping("/ajaxmentorprojects")
	@ResponseBody
	public List<ProjectDto> mentorProjects(@RequestParam("user") Long userId) {
		return projectService.getMentorProjects(userId).stream().map(ProjectDto::new).collect(Collectors.toList());
	}
	
	@RequestMapping(value = "/ajaxcriteria", method = RequestMethod.GET)
	@ResponseBody
	public ProjectReportDto projectCriteria(@RequestParam("projects") List<Long> projects, @RequestParam("student") Long student) {
		
		if(projects.isEmpty()){
			List<Project> projectsEntity = projectService.getStudentProjects(student);
			for(Project project: projectsEntity)
				projects.add(project.getId());
		}
		
		ProjectReportDto projectReportDto = new ProjectReportDto(criterionService.getByProjects(projects), categoryService.getByProjects(projects));

		return projectReportDto;
	}

	@RequestMapping(value = "/ajax/get/projects_final_review", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectDto> getProjectsForFinalReview(@RequestParam("studentId") Long studentId, Principal principal){
		List<ProjectDto> response = new LinkedList<ProjectDto>();
		Long mentorId = userService.getByEmail(principal.getName()).getId();
		Date date = new Date(System.currentTimeMillis());
		for (Project project : projectService.getMentorStudentProjects(mentorId, studentId)){
			if(project.getFinishDate().compareTo(date)>0||(!finalReviewService.existsForProject(studentId, project.getId(), "F")))
				response.add(new ProjectDto(project));
		}
		return response;
	}

	@RequestMapping (value = "/ajax/get/final_review_form")
	@ResponseBody
	public JsonWrapperFinRev getFinReviewFormData(@RequestParam("user") Long userId, @RequestParam("projectId") Long projectId){
		JsonWrapperFinRev wrapper = new JsonWrapperFinRev();
		List<FinalReviewCriterion> result = null;
		Project current = projectService.getById(projectId);
		if(current!=null){
			if(finalReviewService.exists(userId, groupService.getByUserProject(userId, current.getId()).getId(), "F")) {
				FinalReview review = finalReviewService.getByStudent(current.getId(), userId, "F");
				result = finalReviewCriterionService.getByFinalReview(review.getId());
				if(review.getCommentary()!=null)
					wrapper.setComment(review.getCommentary());
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
		wrapper.setData(result);
		return wrapper;
	}

	@RequestMapping(value = "/ajax/post/final_review_form/{id}", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public String postFinRev(Principal principal, @PathVariable("id") Long userId, @RequestBody JsonWrapperFinRev data){
		User mentor = userService.getByEmail(principal.getName());
		List<Project> all = projectService.getStudentProjects(userId);
		Date date = new Date(System.currentTimeMillis());
		//only for testing gonna be fixed ASAP
		Project current = null;
		for(Project project : all)
			if(project.getFinishDate().compareTo(date)>=0){
				current = project;
				break;
			}
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


	@RequestMapping(value = "/createReview", method = RequestMethod.POST)
	public void createHRreview(Principal principal, @RequestParam("type") String type, @RequestParam("commentary") String commentary)
	{
		FinalReview finalHRreview = new FinalReviewImpl();
		finalHRreview.setCommentary(commentary);
		finalHRreview.setType(type);
		Timestamp date = new Timestamp(System.currentTimeMillis());
		finalHRreview.setDate(date);
		User hr = userService.getByEmail(principal.getName());
		finalHRreview.setEmployee(hr);
		finalHRreview.setProject(projectService.getById((long) 3));
		finalHRreview.setStudent(userService.getById((long) 20));
		finalReviewService.createFinalReview(finalHRreview);
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ModelAndView handleEmpyResultDataAccessException(EmptyResultDataAccessException ex) {

		ModelAndView model = new ModelAndView("error/userEmptyData");

		return model;

	}
}
