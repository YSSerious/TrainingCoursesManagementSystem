package ua.ukma.nc.service;

import ua.ukma.nc.entity.GroupAttachment;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface GroupAttachmentService {
    GroupAttachment getById(Long id);

    int deleteGroupAttachment(GroupAttachment groupAttachment);

    int updateGroupAttachment(GroupAttachment groupAttachment);

    List<GroupAttachment> getAll();

    int createGroupAttachment(GroupAttachment groupAttachment);
}
