package ua.ukma.nc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@RequestMapping(value = "/studentMarks", method = RequestMethod.GET)
	public ModelAndView downloadExcel(@RequestParam(name = "projects", required = false) List<Long> projects,
			@RequestParam(name = "criteria", required = false) List<Long> criteria,
			@RequestParam(name = "categories", required = false) List<Long> categories,
			@RequestParam("studentId") Long student) {

		if (projects.isEmpty()) {
			projects = new ArrayList<>();
			List<Project> projectsEntity = projectService.getStudentProjects(student);
			for (Project project : projectsEntity)
				projects.add(project.getId());
		}
		
		Map<String, MarkTableDto> values = new TreeMap<>();

		for (Long projectId : projects) {
			MarkTableDto markTableDto = markTableService.getMarkTableDto(student, projectId, criteria, categories);
			Project project = projectService.getById(projectId);
			
			values.put(project.getName(), markTableDto);
		}

		UserDto user = new UserDto(userService.getById(student));

		ModelAndView modelAndView = new ModelAndView("studentExcelBuilder");
		modelAndView.addObject("values", values);
		modelAndView.addObject("student", user);

		return modelAndView;
	}
}
