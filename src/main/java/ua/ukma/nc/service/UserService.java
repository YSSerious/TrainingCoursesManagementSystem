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

	void deleteRole(User user, Long roleId);

	int count();

	boolean hasReview(Long studentId, Long groupId);

	List<User> getByName(String name, int limit, int offset);

	List<User> studentsByProjectId(Long projectId);

	List<User> studentsByGroupId(Long groupId);
	
	boolean hasStudentProjects(Long studentId);
	boolean hasMentorProjects(Long mentorId);
	boolean hasHRReviews(Long hrId);
	
	boolean isUsingRole(Long userId, Long roleId);
	
	boolean isCurrentUser(Long userId);

	List<User> getByName(String name, String secondName, String lastName, int limit, int offset);

	List<User> getByName(String value1, String value2, int limit, int offset);

	List<User> studentsByProjectName(String name, int limit, int offset);

	List<User> studentsByGroupName(String name, int limit, int offset);

	int countGroup(String name);

	int countProject(String name);

	int countName(String name);

	int countHalfName(String value1, String value2);

	int countFullName(String name, String secondName, String lastName);

	List<User> getByRole(int role1, int role2, int role3, int role4);

	List<User> getFreeMentors();

	List<User> getInactiveStudents();

}
