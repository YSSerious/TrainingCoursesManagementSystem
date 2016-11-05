package ua.ukma.nc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.Project;
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
    private GroupService groupServise;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public void addGroup(@RequestParam("group_name") String groupName, @RequestParam("project_id") Long projectId){
        Project project = new ProjectImpl();
        project.setId(projectId);
        Group group = new GroupImpl();
        group.setProject(project);
        group.setName(groupName);
        groupServise.createGroup(group);
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addGroup(){
        return "group";
    }


}
