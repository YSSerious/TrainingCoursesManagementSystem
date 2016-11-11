
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
import ua.ukma.nc.entity.Project;
import ua.ukma.nc.entity.Role;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.entity.impl.real.GroupImpl;
import ua.ukma.nc.entity.impl.real.ProjectImpl;
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
		
		model.addObject("group",group);
		model.addObject("students",students);
		model.addObject("mentors",mentors);
	//	model.addObject("users",users);
		model.setViewName("group-view");
	
		//for(User user : group.getUsers()){
		//	log.info("users name : "+ user.getFirstName() + " users' role"+ user.getRoles());
	//	}
		
		log.info("Getting group with name : "+group.getName()+" and project: "+group.getProject().getName());
		log.info("Group information sent");
		return model;
	}


}
