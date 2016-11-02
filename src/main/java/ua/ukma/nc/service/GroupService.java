package ua.ukma.nc.service;

import ua.ukma.nc.entity.Group;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface GroupService {
    Group getById(Long id);

    int deleteGroup(Group group);

    int updateGroup(Group group);

    List<Group> getAll();

    int createGroup(Group group);
}
