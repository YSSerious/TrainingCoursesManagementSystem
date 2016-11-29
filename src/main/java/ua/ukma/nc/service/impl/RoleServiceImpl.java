package ua.ukma.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ukma.nc.dao.RoleDao;
import ua.ukma.nc.dto.RoleDto;
import ua.ukma.nc.entity.Role;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.service.RoleService;
import ua.ukma.nc.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleDao roleDao;
    
    @Autowired
    private UserService userService;

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

	@Override
	public List<RoleDto> getRolesDto(User user) {
		List<Role> allRoles = getAll();
		List<Role> userRoles = user.getRoles();
		
		List<RoleDto> rolesDto = new ArrayList<RoleDto>();
		
		for(Role role: allRoles){
			RoleDto roleDto = new RoleDto(role);
			
			for(Role userRole: userRoles)
				if(role.getId() == userRole.getId()){
					roleDto.setHave(true);
					roleDto.setActive(userService.isUsingRole(user.getId(), role.getId()));
					break;
				}
			
			rolesDto.add(roleDto);
		}
		
		return rolesDto;
	}

	@Override
	public void changeRoles(List<Long> chRoles, User user) {
		List<Role> roles = user.getRoles();
		List<Long> rolesId = new ArrayList<Long>();
		
		for(Role role: roles)
			rolesId.add(role.getId());
		
		for(Long roleId: rolesId)
			if(!chRoles.contains(roleId))
				userService.deleteRole(user, roleId);
		
		for(Long roleId: chRoles)
			if(!rolesId.contains(roleId))
				userService.addRole(user, getById(roleId));
	}
}
