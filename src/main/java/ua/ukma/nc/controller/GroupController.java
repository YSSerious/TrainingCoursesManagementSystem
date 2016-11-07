/**
 * 
 */
package ua.ukma.nc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.entity.impl.proxy.GroupProxy;
import ua.ukma.nc.service.GroupService;
import ua.ukma.nc.service.UserService;

/**
 * @author AntBesedkin
 *
 */
@Controller
public class GroupController {
	
	@Autowired
	GroupService groupService;
	
	private static Logger log = LoggerFactory.getLogger(HomeController.class.getName());
	
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
