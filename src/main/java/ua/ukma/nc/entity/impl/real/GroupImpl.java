package ua.ukma.nc.entity.impl.real;

import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.Project;
import ua.ukma.nc.entity.User;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public class GroupImpl implements Group{

    private static final long serialVersionUID = 402473661936943952L;
    private Long id;
    private Project project;
    private String name;
    List<User> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupImpl group = (GroupImpl) o;

        if (id != null ? !id.equals(group.id) : group.id != null) return false;
        if (project != null ? !project.equals(group.project) : group.project != null) return false;
        if (name != null ? !name.equals(group.name) : group.name != null) return false;
        return users != null ? users.equals(group.users) : group.users == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (project != null ? project.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GroupImpl{" +
                "id=" + id +
                ", project=" + project +
                ", name='" + name + '\'' +
                ", users=" + users +
                '}';
    }
}
