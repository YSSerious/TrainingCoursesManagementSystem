package ua.ukma.nc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import ua.ukma.nc.dto.ProjectDto;
import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.GroupAttachment;
import ua.ukma.nc.entity.ProjectAttachment;
import ua.ukma.nc.service.CriterionService;
import ua.ukma.nc.service.GroupService;
import ua.ukma.nc.service.ProjectAttachmentService;
import ua.ukma.nc.service.ProjectService;
import ua.ukma.nc.service.impl.ProjectAttachmentServiceImpl;

@Controller
public class CertainProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private GroupService groupService;
	@Autowired
	private CriterionService criterionService;

	@Autowired
	private ProjectAttachmentService attachmentService;
	
	private Long project_id;
	
	@RequestMapping(value = "/certainProject/{id}", method = RequestMethod.GET)
	public ModelAndView viewProject(@PathVariable("id") Long id) {
		ModelAndView model = new ModelAndView();
		project_id = id;
		
		ProjectDto prDto = new ProjectDto(projectService.getById(id));
		model.addObject("project",prDto);
		
		//Group set
		List<Group> groupList = groupService.getByProjectId(id);
		model.addObject("groups", groupList);

		//Criteria set
		model.addObject("criterions", criterionService.getByProject(id));
		//Attachment set
		List<ProjectAttachment> attachmentList = attachmentService.getAllById(id);
		model.addObject("attachments", attachmentList);
		List<Group> groups = groupService.getByProjectId(id);
		model.addObject("groups", groups);

		model.setViewName("certainProject");
		return model;
	}
	
	@RequestMapping(value = "/addProjectAttachment", method = RequestMethod.POST)
	public void addGroupAttachment
	(   @RequestParam("attachmentName") String attachmentName,
		@RequestParam("attachmentLink") String attachmentLink){
		
		ProjectAttachment att = new ProjectAttachment();
		att.setName(attachmentName);
		att.setAttachmentScope(attachmentLink);
		att.setProject(projectService.getById(project_id));
		attachmentService.createProjectAttachment(att);
		
	}
	
	@RequestMapping(value = "/removeProjectAttachment", method = RequestMethod.POST)
	public void removeGroupAttachment (@RequestParam("id_attachment") Long id){
		attachmentService.deleteProjectAttachment(attachmentService.getById(id));
	}
	
	


	@RequestMapping(value = "/getAvailableCriteria", method = RequestMethod.GET)
	@ResponseBody
	public List<Criterion> getAvailableCriteria(@RequestParam Long projectId){
		return criterionService.getProjectUnusedCriteria(projectId);
	}


}
