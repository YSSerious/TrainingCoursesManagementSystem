
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
import org.springframework.web.servlet.ModelAndView;

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
		List<UserDto> users = group.getUsers();
		List<UserDto> students = new ArrayList<UserDto>();
		List<UserDto> mentors = new ArrayList<UserDto>();
		 
	 

		List<GroupAttachment> groupAttachments= groupAttachmentService.getAll();
		System.out.println(groupAttachments.size()+"Size of ");
		//List<GroupAttachment> groupAttachmentsFinal= new ArrayList<GroupAttachment>();
		for(UserDto us: users){

			boolean isMentor=false;
			List<String> roles = us.getRoles();
			for(String r : roles){
				if(r.equals("ROLE_MENTOR")){
					mentors.add(us);
					isMentor=true;
				}
				
			}
			if(!isMentor) students.add(us);
		}

		List<MeetingDto> meetings = new ArrayList<MeetingDto>();
		for(Meeting mt : meetingService.getByGroup(id) ){
			meetings.add(new MeetingDto(mt));
		}
 
	 
		/*
		for(GroupAttachment attach:groupAttachments){
			if(attach.getGroup().equals(group))
				groupAttachmentsFinal.add(attach);			
		}
 */
	
	//	groupAttachmentsFinal.add(attachment);
	 
		 
		model.addObject("group-name",group.getName());
		model.addObject("group-project",group.getProject().getName());
 
		String projectName = group.getProject().getName();
		model.addObject("group",group);
		model.addObject("projectName",projectName);
 		
 
	 
		model.addObject("group-name",group.getName());
		model.addObject("group-project",group.getProject().getName());

	//	String projectName = group.getProject().getName();
		model.addObject("group",group);
		model.addObject("projectName",projectName);

 
		model.addObject("students",students);
		model.addObject("mentors",mentors);
		model.addObject("meetings",meetings);
		//model.addObject("attachments",groupAttachmentsFinal);
		model.addObject("group-id",group.getId());

		model.addObject("attachments",groupAttachments);
		model.addObject("groupId",group.getId());

		model.setViewName("group-view");
	
		//for(User user : group.getUsers()){
		//	log.info("users name : "+ user.getFirstName() + " users' role"+ user.getRoles());
	//	}
		
		log.info("Getting group with name : "+group.getName()+" and project: "+group.getProject().getName());
		log.info("Group information sent");
		return model;
	}
	
	@RequestMapping(value = "addAttachment", method = RequestMethod.POST)
	public void addGroupAttachment(@RequestParam("id_group") Long idGroup,@RequestParam("name") String name,
		@RequestParam("attachment_scope") String attachmentScope){
		GroupAttachment attachment =new GroupAttachment();
		attachment.setAttachmentScope(attachmentScope);
		attachment.setGroup(groupService.getById(idGroup));
		attachment.setName(name);
		groupAttachmentService.createGroupAttachment(attachment);
		
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
	@RequestMapping(value = "deleteAttachment", method = RequestMethod.POST)
	public void deleteGroupAttachment(@RequestParam("id") Long idAttachment){
		GroupAttachment attachment =groupAttachmentService.getById(idAttachment);
		groupAttachmentService.deleteGroupAttachment(attachment);
		
	}
	
	
}
