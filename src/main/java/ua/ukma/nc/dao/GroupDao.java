package ua.ukma.nc.dao;

import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.User;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface GroupDao {
	
	List<Group> getByProjectId(Long projectId);
	
    Group getById(Long id);

    int deleteGroup(Group group);

    int updateGroup(Group group);

    List<Group> getAll();
    
    List<User> getStudents(Long groupId);
    
    List<User> getMentors(Long groupId);
    
    int createGroup(Group group);
    
}
