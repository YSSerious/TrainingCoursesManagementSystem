package ua.ukma.nc.dao;

import ua.ukma.nc.entity.Role;
import ua.ukma.nc.entity.Status;
import ua.ukma.nc.entity.StudentStatus;
import ua.ukma.nc.entity.User;

import java.util.List;

/**
 * Created by Алексей on 15.10.2016.
 */
public interface UserDao {
	
	boolean canView(String mentorEmail, Long studentId);

    User getByEmail(String email);

    User getById(Long id);

    int deleteUser(User user);

    int updateUser(User user);

    List<User> getAll();

    int createUser(User user);

    boolean isExist(User user);

    int addRole(User user, Role role);
    
    void deleteRole(User user, Long roleId);

    int setStatus(User user, Status status);

	List<User> getSome(int limit, int offset);
	
	boolean hasReviews(Long studentId , Long groupId);

	int count();
	
	List<User> studentsByProjectId(Long projectId);
	
	List<User> studentsByGroupId(Long groupId);

	List<User> getByName(String name);
	
	boolean hasStudentProjects(Long studentId);
	boolean hasMentorProjects(Long mentorId);
	boolean hasHRReviews(Long hrId);

}
