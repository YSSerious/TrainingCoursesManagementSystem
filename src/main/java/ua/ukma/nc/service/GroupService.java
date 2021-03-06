package ua.ukma.nc.service;

import ua.ukma.nc.dto.AttendanceTable;
import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.User;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface GroupService {
	
	boolean canView(Long groupId);
	
	AttendanceTable getAttendaceTable(Long groupId);
	
	List<Group> getByProjectId(Long projectId);
	
    Group getById(Long id);

    Group getByName(String name);

    List<Group> getByNames(List<String> names);

    Group getByUserProject(Long userId, Long projectId);

    int deleteGroup(Group group);
    
    int removeMentor(Long groupId,Long userid);

    int updateGroup(Group group);

    List<Group> getAll();
    
    List<User> getMentors(Long groupId);
    
    List<User> getStudents(Long groupId);

    Long getStudentsAmount(Long groupId);
    
    int createGroup(Group group);

	int removeStudent(Long groupId, Long userId);

    void addUser(Long groupId, Long userId);
}
