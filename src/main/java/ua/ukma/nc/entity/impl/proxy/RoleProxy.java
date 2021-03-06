package ua.ukma.nc.entity.impl.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.ukma.nc.entity.Role;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.entity.impl.real.RoleImpl;
import ua.ukma.nc.service.RoleService;

import java.util.List;

/**
 * Created by Алексей on 01.11.2016.
 */
@Component
@Scope("prototype")
public class RoleProxy implements Role{

    private static final long serialVersionUID = 4940512134174422581L;

    private Long id;

    private RoleImpl role;

    @Autowired
    private RoleService roleService;

    public RoleProxy() {
    }

    public RoleProxy(Long id) {
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
    public String getTitle() {
        downloadRole();
        return role.getTitle();
    }

    @Override
    public void setTitle(String title) {
        downloadRole();
        role.setTitle(title);
    }

    private void downloadRole() {
        if (role == null) {
            role = (RoleImpl) roleService.getById(id);
        }
    }

    @Override
    public String toString() {
        return "Proxy "+id;
    }
}
