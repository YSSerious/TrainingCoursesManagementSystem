package ua.ukma.nc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ua.ukma.nc.dto.ProjectDto;
import ua.ukma.nc.service.ProjectService;

@Controller
public class CertainProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value = "/certainProject/{name}", method = RequestMethod.GET)
	public ModelAndView viewProject(@PathVariable("name") String name) {
		ModelAndView model = new ModelAndView();
		
		ProjectDto prDto = new ProjectDto(projectService.getByName(name));
		model.addObject("project",prDto);
		model.setViewName("project");
		return model;
	}
}
