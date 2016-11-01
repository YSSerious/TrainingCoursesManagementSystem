package ua.ukma.nc.entity.impl.real;

import ua.ukma.nc.entity.Role;
import ua.ukma.nc.entity.User;

import java.util.List;

/**
 * Created by Алексей on 01.11.2016.
 */
public class RoleImpl implements Role{

    private static final long serialVersionUID = -5795710302084316125L;
    private Long id;
    private String title;
    private List<User> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

        RoleImpl role = (RoleImpl) o;

        if (id != null ? !id.equals(role.id) : role.id != null) return false;
        if (title != null ? !title.equals(role.title) : role.title != null) return false;
        return users != null ? users.equals(role.users) : role.users == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RoleImpl{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", users=" + users +
                '}';
    }
}
