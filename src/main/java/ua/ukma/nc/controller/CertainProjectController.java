package ua.ukma.nc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ua.ukma.nc.dto.ProjectDto;
import ua.ukma.nc.entity.Group;
import ua.ukma.nc.service.GroupService;
import ua.ukma.nc.service.ProjectService;

@Controller
public class CertainProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private GroupService groupService;
	
	@RequestMapping(value = "/certainProject/{id}", method = RequestMethod.GET)
	public ModelAndView viewProject(@PathVariable("id") Long id) {
		ModelAndView model = new ModelAndView();
		
		ProjectDto prDto = new ProjectDto(projectService.getById(id));
		model.addObject("project",prDto);
		
		List<Group> groups = groupService.getByProjectId(id);
		model.addObject("groups", groups);
		
		model.setViewName("certainProject");
		return model;
	}
}
