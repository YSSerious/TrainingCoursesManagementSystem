package ua.ukma.nc.service;

import ua.ukma.nc.dto.RoleDto;
import ua.ukma.nc.entity.Role;
import ua.ukma.nc.entity.User;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface RoleService {
	
	List<Role> getCurrentUserRoles(String email, Long userId);

    Role getById(Long id);

    boolean isExist(Role role);

    int deleteRole(Role role);

    int updateRole(Role role);

    List<Role> getAll();

    int createRole(Role role);

	Role getByRole(String role);
	
	List<RoleDto> getRolesDto(User user);
	
	void changeRoles(List<Long> chRoles, User user);
}
