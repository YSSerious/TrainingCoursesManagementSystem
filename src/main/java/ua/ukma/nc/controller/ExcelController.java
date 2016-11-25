package ua.ukma.nc.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ua.ukma.nc.dto.ProjectReportItemDto;
import ua.ukma.nc.dto.StudentProfile;
import ua.ukma.nc.dto.UserDto;
import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.Project;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.service.GroupService;
import ua.ukma.nc.service.ProjectService;
import ua.ukma.nc.service.StatusLogService;
import ua.ukma.nc.service.StudentService;
import ua.ukma.nc.service.UserService;

@Controller
public class ExcelController {
	
	@Autowired
	private GroupService groupService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private UserService userService;

    @Autowired
    private StatusLogService statusLogService;
    
	@RequestMapping(value = "/projectReport", method = RequestMethod.GET)
	public ModelAndView projectReport(@RequestParam(name = "students", required = false) List<Long> students,
			@RequestParam(name = "criteria", required = false) List<Long> criteria,
			@RequestParam(name = "categories", required = false) List<Long> categories,
			@RequestParam("projectId") Long projectId) {

		if(students == null)
			students = new ArrayList<Long>();
		
		if(criteria == null)
			criteria = new ArrayList<Long>();
		
		if(categories == null)
			categories = new ArrayList<Long>();
		
		if (students.isEmpty()) {
			students = new ArrayList<>();
			List<User> users = userService.studentsByProjectId(projectId);
			for (User user : users)
				students.add(user.getId());
		}
		
		Map<String, StudentProfile> values = new TreeMap<>();

		for (Long studentId : students) {
			StudentProfile studentProfile = studentService.generateStudentProfile(studentId, projectId, criteria, categories);
			User user = userService.getById(studentId);
			
			values.put(user.getLastName()+" " +user.getFirstName(), studentProfile);
		}

		ModelAndView modelAndView = new ModelAndView("projectExcelBuilder");
		modelAndView.addObject("values", values);
		modelAndView.addObject("title", projectService.getById(projectId).getName());

		return modelAndView;
	}

	@RequestMapping(value = "/groupReport", method = RequestMethod.GET)
	public ModelAndView groupReport(@RequestParam(name = "students", required = false) List<Long> students,
			@RequestParam(name = "criteria", required = false) List<Long> criteria,
			@RequestParam(name = "categories", required = false) List<Long> categories,
			@RequestParam("groupId") Long groupId) {

		if(students == null)
			students = new ArrayList<Long>();
		
		if(criteria == null)
			criteria = new ArrayList<Long>();
		
		if(categories == null)
			categories = new ArrayList<Long>();
		
		if (students.isEmpty()) {
			students = new ArrayList<>();
			List<User> users = userService.studentsByGroupId(groupId);
			for (User user : users)
				students.add(user.getId());
		}
		
		Group group = groupService.getById(groupId);
		Long projectId = group.getProject().getId();
		
		Map<String, StudentProfile> values = new TreeMap<>();

		for (Long studentId : students) {
			StudentProfile studentProfile = studentService.generateStudentProfile(studentId, projectId, criteria, categories);
			User user = userService.getById(studentId);
			
			values.put(user.getLastName()+" " +user.getFirstName(), studentProfile);
		}
		
		ModelAndView modelAndView = new ModelAndView("groupExcelBuilder");
		modelAndView.addObject("title", group.getName());
		modelAndView.addObject("values", values);

		return modelAndView;
	}
	
	@RequestMapping(value = "/studentMarks", method = RequestMethod.GET)
	public ModelAndView studentReport(@RequestParam(name = "projects", required = false) List<Long> projects,
			@RequestParam(name = "criteria", required = false) List<Long> criteria,
			@RequestParam(name = "categories", required = false) List<Long> categories,
			@RequestParam("studentId") Long student) {

		if (projects.isEmpty()) {
			projects = new ArrayList<>();
			List<Project> projectsEntity = projectService.getStudentProjects(student);
			for (Project project : projectsEntity)
				projects.add(project.getId());
		}
		
		Map<String, StudentProfile> values = new TreeMap<>();

		for (Long projectId : projects) {
			StudentProfile studentProfile = studentService.generateStudentProfile(student, projectId, criteria, categories);
			Project project = projectService.getById(projectId);
			
			values.put(project.getName(), studentProfile);
		}

		UserDto user = new UserDto(userService.getById(student));

		ModelAndView modelAndView = new ModelAndView("studentExcelBuilder");
		modelAndView.addObject("values", values);
		modelAndView.addObject("title", user.getLastName() + " " + user.getFirstName());

		return modelAndView;
	}

	@RequestMapping(value = "/reports")
    public ModelAndView getReport(){
        ModelAndView mv = new ModelAndView("projectReports");
        mv.addObject("projects", projectService.getAll());
        return mv;
    }

    @RequestMapping (value = "/reports/get")
	public ModelAndView doGetReport(@RequestParam Long[] projectIds){
        List<ProjectReportItemDto> data = new LinkedList<>();
        ProjectReportItemDto item = null;
        for(Long id : projectIds){
            item = new ProjectReportItemDto();
            item.setProjectId(id);
            item.setProjectName(projectService.getById(id).getName());
            item.setNumOfStarted(statusLogService.getNumOfStartedProject(id));
            item.setNumOfWasInvited(statusLogService.getNumOfInvitedByProject(id));
            item.setNumOfNotInvited(item.getNumOfStarted()-item.getNumOfWasInvited());
            item.setNumOfJobOffer(statusLogService.getNumOfJobOffersByProject(id));
            data.add(item);
        }
        ModelAndView mv = new ModelAndView("excelProjectReports");
        mv.addObject("data", data);
		return mv;
	}
}
