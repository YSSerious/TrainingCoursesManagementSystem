package ua.ukma.nc.dao;

import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.User;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface GroupDao {
	
	List<Group> getByProjectId(Long projectId);
	
	List<Group> getByProjectId(Long projectId, Long mentorId);
	
    Group getById(Long id);

    Group getByName(String name);

    int deleteGroup(Group group);

    int updateGroup(Group group);
    
    int removeMentor(Long groupId,Long userId);
    
    int removeStudent(Long groupId,Long userId);

    List<Group> getAll();
    
    List<User> getStudents(Long groupId);
    
    Long getStudentsAmount(Long groupId);
    
    List<User> getMentors(Long groupId);
    
    int createGroup(Group group);

    Group getByUserProject(Long userId, Long projectId);

    void addUser(Long groupId, Long studentId);
}
