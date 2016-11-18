package ua.ukma.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ukma.nc.dao.GroupDao;
import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.service.GroupService;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Service
public class GroupServiceImpl implements GroupService{

    @Autowired
    private GroupDao groupDao;

    @Override
    public Group getById(Long id) {
        return groupDao.getById(id);
    }

    @Override
    public int deleteGroup(Group group) {
        return groupDao.deleteGroup(group);
    }

    @Override
    public int updateGroup(Group group) {
        return groupDao.updateGroup(group);
    }

    @Override
    public List<Group> getAll() {
        return groupDao.getAll();
    }

    @Override
    public int createGroup(Group group) {
        return groupDao.createGroup(group);
    }

	@Override
	public List<Group> getByProjectId(Long projectId) {
		return groupDao.getByProjectId(projectId);
	}

	@Override
	public List<User> getMentors(Long groupId) {
		return groupDao.getMentors(groupId);
	}

	@Override
	public List<User> getStudents(Long groupId) {
		return groupDao.getStudents(groupId);
	}

        @Override
        public Long getStudentsAmount(Long groupId) {
            return groupDao.getStudentsAmount(groupId);
        }
        
	@Override
	public int removeMentor(Long groupId, Long userId) {
		return groupDao.removeMentor(groupId, userId);
	}

	@Override
	public int removeStudent(Long groupId, Long userId) {
		return groupDao.removeStudent(groupId, userId);
	}
}
