package ua.ukma.nc.dao.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.ukma.nc.config.TestConfig;
import ua.ukma.nc.dao.GroupAttachmentDao;
import ua.ukma.nc.dao.GroupDao;
import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.GroupAttachment;

import java.util.List;

/**
 * Created by Алексей on 06.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class GroupAttachmentDaoTest {
    @Autowired
    private GroupAttachmentDao groupAttachmentDao;
    @Autowired
    private GroupDao groupDao;

    @Test
    public void getByIdTest() {
        GroupAttachment groupAttachment= groupAttachmentDao.getById(1L);
        Assert.assertNotNull(groupAttachment);
    }

    @Test
    public void getAllTest() {
        List<GroupAttachment> groupAttachments= groupAttachmentDao.getAll();
        Assert.assertNotNull(groupAttachments);
    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() {
        GroupAttachment groupAttachment= groupAttachmentDao.getById(1L);
        groupAttachment.setName("newName");
        Assert.assertEquals(1, groupAttachmentDao.updateGroupAttachment(groupAttachment));
        Assert.assertEquals(groupAttachment.getName(), groupAttachmentDao.getById(groupAttachment.getId()).getName());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteTest() {
        GroupAttachment groupAttachment= groupAttachmentDao.getById(1L);
        Assert.assertEquals(1, groupAttachmentDao.deleteGroupAttachment(groupAttachment));
    }

    @Test
    @Transactional
    @Rollback
    public void createTest() {
        GroupAttachment groupAttachment= new GroupAttachment();
        Group group = groupDao.getById(8L);
        groupAttachment.setName("Name");
        groupAttachment.setGroup(group);
        groupAttachment.setAttachmentScope("attachScope");
        Assert.assertNotNull(groupAttachmentDao.createGroupAttachment(groupAttachment));
    }
}
