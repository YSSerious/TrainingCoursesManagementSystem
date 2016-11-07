
package ua.ukma.nc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.Project;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.entity.impl.real.GroupImpl;
import ua.ukma.nc.entity.impl.real.ProjectImpl;
import ua.ukma.nc.service.GroupService;

/**
 * Created by Nastasia on 05.11.2016.
 */
@Controller
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

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
		Group group = groupService.getById(id);
		
	//	System.out.println("group name : "+group.getName()+" project: "+group.getProject().getName());
		model.addObject("group-name",group.getName());
		model.addObject("group-project",group.getProject().getName());
		model.addObject("users",group.getUsers());
		model.setViewName("group-view");
	//	System.out.println(group.getUsers().size());
		for(User us : group.getUsers()){
			log.info("users name : "+ us.getFirstName());
		}
		
		log.info("Getting group with name : "+group.getName()+"and project: "+group.getProject().getName());
		log.info("Group information sent");
		return model;
	}


}
