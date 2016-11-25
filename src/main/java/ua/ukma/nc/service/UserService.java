package ua.ukma.nc.service;

import ua.ukma.nc.entity.Role;
import ua.ukma.nc.entity.Status;
import ua.ukma.nc.entity.User;

import java.util.List;

/**
 * Created by Алексей on 15.10.2016.
 */
public interface UserService {

	boolean canView(Long id);

	void changeStatus(Long id, Long statusId, String commentary);

	User getByEmail(String email);

	User getById(Long id);

	int deleteUser(User user);

	int updateUser(User user);

	List<User> getAll();

	void createUser(User user, List<Role> roles);

	boolean isExist(User user);

	int addRole(User user, Role role);

	int setStatus(User user, Status status);

	List<User> getSome(int limit, int offset);

	void deleteRoles(User user);

	int count();

	boolean hasReview(Long studentId, Long groupId);

	List<User> getByName(String name);

	List<User> studentsByProjectId(Long projectId);

	List<User> studentsByGroupId(Long groupId);

}
