package ua.ukma.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import ua.ukma.nc.dao.RoleDao;
import ua.ukma.nc.dao.impl.RoleDaoImpl.RoleMapper;
import ua.ukma.nc.entity.Role;
import ua.ukma.nc.service.RoleService;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleDao roleDao;

    @Override
    public Role getById(Long id) {
        return roleDao.getById(id);
    }

    @Override
    public boolean isExist(Role role) {
        return roleDao.isExist(role);
    }

    @Override
    public int deleteRole(Role role) {
        return roleDao.deleteRole(role);
    }

    @Override
    public int updateRole(Role role) {
        return roleDao.updateRole(role);
    }

    @Override
    public List<Role> getAll() {
        return roleDao.getAll();
    }

    @Override
    public int createRole(Role role) {
        return roleDao.createRole(role);
    }

	@Override
	public List<Role> getCurrentUserRoles(String email, Long userId) {
		return roleDao.getCurrentUserRoles(email, userId);
	}
	
	@Override
    public Role getByRole(String role) {
        return roleDao.getByRole(role);
    }
}
