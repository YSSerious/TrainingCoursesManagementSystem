package ua.ukma.nc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.ukma.nc.dto.*;
import ua.ukma.nc.entity.*;
import ua.ukma.nc.entity.impl.real.FinalReviewImpl;
import ua.ukma.nc.service.*;

import java.security.Principal;
import java.sql.Date;
import java.sql.Timestamp;
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
	
	private static Logger log = LoggerFactory.getLogger(HomeController.class.getName());

	@RequestMapping(value = "/certainUser/{id}", method = RequestMethod.GET)
	public String getCertainUser(Model model, Principal principal, @PathVariable("id") Long id) {
		User user = userService.getById(id);
		model.addAttribute("user", user);
		return "certainUser";
	}
		
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ModelAndView viewUser(@PathVariable("id") Long id) {
		ModelAndView model = new ModelAndView("user");
		
		User user = userService.getById(id);
		UserDto userDto = new UserDto(user);
		
		model.addObject("user", userDto);
		model.addObject("title", userDto.getLastName()+" "+userDto.getFirstName());
		model.addObject("roles", roleService.getRolesDto(user));
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
		//if input validation cheated on the client side
		for(FinalReviewCriterion frc : data.getData())
			if(frc.getCommentary().length()>255||frc.getCommentary().isEmpty())
				return "false";

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
				if(frc.getCommentary().length()>255)
					return "false";
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
}
