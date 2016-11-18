
package ua.ukma.nc.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ua.ukma.nc.dto.GroupAttachmentDto;
import ua.ukma.nc.dto.GroupDto;
import ua.ukma.nc.dto.MeetingDto;
import ua.ukma.nc.dto.UserDto;
import ua.ukma.nc.entity.Group;

import ua.ukma.nc.entity.Meeting;

import ua.ukma.nc.entity.GroupAttachment;

import ua.ukma.nc.entity.Project;
import ua.ukma.nc.entity.Role;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.entity.impl.real.GroupImpl;
import ua.ukma.nc.entity.impl.real.ProjectImpl;
import ua.ukma.nc.service.GroupAttachmentService;
import ua.ukma.nc.service.GroupService;
import ua.ukma.nc.service.MeetingService;
import ua.ukma.nc.service.RoleService;
import ua.ukma.nc.util.exception.CriteriaDeleteException;

/**
 * Created by Nastasia on 05.11.2016.
 */
@Controller
@RequestMapping("/groups")
public class GroupController {
	@Autowired
	private GroupAttachmentService groupAttachmentService;
	
    @Autowired
    private GroupService groupService;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private MeetingService meetingService;
    
    private static Logger log = LoggerFactory.getLogger(HomeController.class.getName());
    
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public void addGroup(@RequestParam("group_name") String groupName, @RequestParam("project_id") Long projectId){
        Project project = new ProjectImpl();
        project.setId(projectId);
        Group group = new GroupImpl();
        group.setProject(project);
        group.setName(groupName);
        groupService.createGroup(group);
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addGroup(){
        return "group";
    }

	
	@RequestMapping(value = "/group", method = RequestMethod.GET)
	public ModelAndView getGroup(@RequestParam Long id) {
		
		ModelAndView model = new ModelAndView();
		GroupDto group = new GroupDto(groupService.getById(id));
		List<User> students = groupService.getStudents(id);
		
		List<User> mentors = groupService.getMentors(id);

		List<GroupAttachment> groupAttachments= groupAttachmentService.getByGroup(id);
		System.out.println(groupAttachments.size()+"Size of ");
	
		List<Meeting> meetings =meetingService.getByGroup(id) ;
	 
		String projectName = group.getProject().getName();
		model.addObject("group",group);
		model.addObject("projectName",projectName);

 
		model.addObject("students",students);
		model.addObject("mentors",mentors);
		model.addObject("meetings",meetings);
		model.addObject("group-id",group.getId());
		model.addObject("attachments",groupAttachments);
		model.addObject("groupId",group.getId());

		model.setViewName("group-view");
	
		
		log.info("Getting group with name : "+group.getName()+" and project: "+group.getProject().getName());
		log.info("Group information sent");
		return model;
	}
	
	@RequestMapping(value = "/addAttachment", method = RequestMethod.POST)
	public void addGroupAttachment(@RequestParam("id_group") Long idGroup,@RequestParam("name") String name,
		@RequestParam("attachment_scope") String attachmentScope){
		GroupAttachment attachment =new GroupAttachment();
		attachment.setAttachmentScope(attachmentScope);
		attachment.setGroup(groupService.getById(idGroup));
		attachment.setName(name);
		groupAttachmentService.createGroupAttachment(attachment);
		
	}
	@RequestMapping(value = "/removeMentor", method = RequestMethod.POST)
    @ResponseBody
    public String removeMentor(@RequestParam Long groupId, @RequestParam Long userId)  {
        groupService.removeMentor(groupId, userId);
     return "Deleted successfully";
    }
	
	@RequestMapping(value = "/removeStudent", method = RequestMethod.POST)
    @ResponseBody
    public String removeStudent(@RequestParam Long groupId, @RequestParam Long userId)  {
        groupService.removeStudent(groupId, userId);
     return "Deleted successfully";
    }
	
	@RequestMapping(value = "editAttachment", method = RequestMethod.POST)
	public void editGroupAttachment(@RequestParam("id_group") Long idGroup,@RequestParam("name") String name,
		@RequestParam("attachment_scope") String attachmentScope,@RequestParam("id") Long idAttachment){
		GroupAttachment attachment =new GroupAttachment();
		attachment.setId(idAttachment);
		attachment.setAttachmentScope(attachmentScope);
		attachment.setGroup(groupService.getById(idGroup));
		attachment.setName(name);
		groupAttachmentService.updateGroupAttachment(attachment);
		
	}
	@RequestMapping(value = "/deleteAttachment", method = RequestMethod.POST)
	public void deleteGroupAttachment(@RequestParam("id") Long idAttachment){
		GroupAttachment attachment =groupAttachmentService.getById(idAttachment);
		groupAttachmentService.deleteGroupAttachment(attachment);
		
	}
	
	
}
