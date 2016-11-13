package ua.ukma.nc.service;

import ua.ukma.nc.entity.Role;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface RoleService {
	
	List<Role> getByUserId(String email, Long userId);

    Role getById(Long id);

    boolean isExist(Role role);

    int deleteRole(Role role);

    int updateRole(Role role);

    List<Role> getAll();

    int createRole(Role role);
}
