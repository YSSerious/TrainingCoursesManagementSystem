package ua.ukma.nc.entity.impl.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import ua.ukma.nc.entity.Role;
import ua.ukma.nc.entity.StudentStatus;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.entity.impl.real.UserImpl;
import ua.ukma.nc.service.UserService;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public class UserProxy implements User{

    private static final long serialVersionUID = -203078030303968859L;

    private Long id;

    private UserImpl user;

    @Autowired
    private UserService userService;

    public UserProxy() {
    }

    public UserProxy(Long id) {
        this.id = id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setFirstName(String fName) {
        downloadUser();
        user.setFirstName(fName);
    }

    @Override
    public String getFirstName() {
        downloadUser();
        return user.getFirstName();
    }

    @Override
    public void setSecondName(String sName) {
        downloadUser();
        user.setSecondName(sName);
    }

    @Override
    public String getSecondName() {
        downloadUser();
        return user.getSecondName();
    }

    @Override
    public void setLastName(String lName) {
        downloadUser();
        user.setLastName(lName);
    }

    @Override
    public String getLastName() {
        downloadUser();
        return user.getLastName();
    }

    @Override
    public void setPassword(String password) {
        downloadUser();
        user.setPassword(password);
    }

    @Override
    public String getPassword() {
        downloadUser();
        return user.getPassword();
    }

    @Override
    public void setEmail(String email) {
        downloadUser();
        user.setEmail(email);
    }

    @Override
    public String getEmail() {
        downloadUser();
        return user.getEmail();
    }

    @Override
    public void setActive(boolean active) {
        downloadUser();
        user.setActive(active);
    }

    @Override
    public boolean isActive() {
        downloadUser();
        return user.isActive();
    }

    @Override
    public List<Role> getRoles() {
        downloadUser();
        return user.getRoles();
    }

    @Override
    public void setRoles(List<Role> roles) {
        downloadUser();
        user.setRoles(roles);
    }

    @Override
    public StudentStatus getStudentStatus() {
        downloadUser();
        return user.getStudentStatus();
    }

    @Override
    public void setStudentStatus(StudentStatus studentStatus) {
        downloadUser();
        user.setStudentStatus(studentStatus);
    }

    private void downloadUser() {
        if (user == null) {
            user = (UserImpl) userService.getById(id);
        }
    }

    @Override
    public String toString() {
        return "Proxy "+id;
    }
}
