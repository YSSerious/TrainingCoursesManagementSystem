package ua.ukma.nc.entity.impl.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.Project;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.entity.impl.real.GroupImpl;
import ua.ukma.nc.service.GroupService;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Component
@Scope("prototype")
public class GroupProxy implements Group {

    private static final long serialVersionUID = -6242955854133156015L;
    private Long id;
    private GroupImpl group;
    @Autowired
    private GroupService groupService;

    public GroupProxy(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Project getProject() {
        downloadGroup();
        return group.getProject();
    }

    @Override
    public void setProject(Project project) {
        downloadGroup();
        group.setProject(project);
    }

    @Override
    public String getName() {
        downloadGroup();
        return group.getName();
    }

    @Override
    public void setName(String name) {
        downloadGroup();
        group.setName(name);
    }

    @Override
    public List<User> getUsers() {
        downloadGroup();
        return group.getUsers();
    }

    @Override
    public void setUsers(List<User> users) {
        downloadGroup();
        group.setUsers(users);
    }

    private void downloadGroup() {
        if (group == null) {
            group = (GroupImpl) groupService.getById(id);
        }
    }

    @Override
    public String toString() {
        return "Proxy " + id;
    }
}
