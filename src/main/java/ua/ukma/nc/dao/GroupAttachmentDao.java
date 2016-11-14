package ua.ukma.nc.dao;

import ua.ukma.nc.entity.GroupAttachment;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface GroupAttachmentDao {
    GroupAttachment getById(Long id);

    int deleteGroupAttachment(GroupAttachment groupAttachment);

    int updateGroupAttachment(GroupAttachment groupAttachment);

    List<GroupAttachment> getAll();
    
    List<GroupAttachment> getByGroupId(Long groupId);

    int createGroupAttachment(GroupAttachment groupAttachment);
}
