package ua.ukma.nc.dao;

import ua.ukma.nc.entity.Role;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface RoleDao {

    Role getById(Long id);

    boolean isExist(Role role);

    int deleteRole(Role role);

    int updateRole(Role role);

    List<Role> getAll();
    
    List<Role> getCurrentUserRoles(String email, Long userId);

    int createRole(Role role);
}
