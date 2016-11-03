package ua.ukma.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ukma.nc.dao.GroupAttachmentDao;
import ua.ukma.nc.entity.GroupAttachment;
import ua.ukma.nc.service.GroupAttachmentService;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Service
public class GroupAttachmentServiceImpl implements GroupAttachmentService{
    @Autowired
    private GroupAttachmentDao groupAttachmentDao;

    @Override
    public GroupAttachment getById(Long id) {
        return groupAttachmentDao.getById(id);
    }

    @Override
    public int deleteGroupAttachment(GroupAttachment groupAttachment) {
        return groupAttachmentDao.deleteGroupAttachment(groupAttachment);
    }

    @Override
    public int updateGroupAttachment(GroupAttachment groupAttachment) {
        return groupAttachmentDao.updateGroupAttachment(groupAttachment);
    }

    @Override
    public List<GroupAttachment> getAll() {
        return groupAttachmentDao.getAll();
    }

    @Override
    public int createGroupAttachment(GroupAttachment groupAttachment) {
        return groupAttachmentDao.createGroupAttachment(groupAttachment);
    }
}
