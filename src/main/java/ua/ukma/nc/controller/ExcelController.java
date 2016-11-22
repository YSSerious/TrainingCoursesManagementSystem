package ua.ukma.nc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ua.ukma.nc.dto.MarkTableDto;
import ua.ukma.nc.dto.UserDto;
import ua.ukma.nc.entity.Project;
import ua.ukma.nc.service.MarkTableService;
import ua.ukma.nc.service.ProjectService;
import ua.ukma.nc.service.UserService;

@Controller
public class ExcelController {
	
	@Autowired
	private MarkTableService markTableService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/studentMarks/{student}/{projects}/{categories}/{criteria}", method = RequestMethod.GET)
	public ModelAndView downloadExcel(@PathVariable("projects") String projects,
			@PathVariable("criteria") String criteria, @PathVariable("categories") String categories,
			@PathVariable("student") Long student) {
		
		String[] params = projects.split("\\D");
		List<Long> projectsId = new ArrayList<Long>();
		
		for(String param: params)
			if(!param.equals(""))
			projectsId.add(Long.valueOf(param));
		
		if(projectsId.isEmpty()){
			List<Project> projectsEntity = projectService.getStudentProjects(student);
			for(Project project: projectsEntity)
				projectsId.add(project.getId());
		}
		
		params = criteria.split("\\D");
		List<Long> criteriaId = new ArrayList<Long>();
		
		for(String param: params)
			if(!param.equals(""))
			criteriaId.add(Long.valueOf(param));
		
		params = categories.split("\\D");
		List<Long> categoriesId = new ArrayList<Long>();
		
		for(String param: params)
			if(!param.equals(""))
				categoriesId.add(Long.valueOf(param));
		
		List<MarkTableDto> markTablesDto = new ArrayList<MarkTableDto>();
		List<String> names = new ArrayList<String>();
		
		for(Long projectId: projectsId){
			MarkTableDto markTableDto = markTableService.getMarkTableDto(student, projectId, criteriaId, categoriesId);
			markTablesDto.add(markTableDto);
			
			Project project = projectService.getById(projectId);
			names.add(project.getName());
		}
		
		UserDto user = new UserDto(userService.getById(student));
		
		ModelAndView modelAndView = new ModelAndView("excelBuilder");
		modelAndView.addObject("markTablesDto", markTablesDto);
		modelAndView.addObject("names", names);
		modelAndView.addObject("student", user);
		
		return modelAndView;
	}
}
