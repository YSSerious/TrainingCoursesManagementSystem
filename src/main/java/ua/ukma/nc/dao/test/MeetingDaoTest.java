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
import ua.ukma.nc.dao.GroupDao;
import ua.ukma.nc.dao.MeetingDao;
import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.Meeting;
import ua.ukma.nc.entity.impl.real.MeetingImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by Алексей on 05.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class MeetingDaoTest {
    @Autowired
    private MeetingDao meetingDao;
    @Autowired
    private GroupDao groupDao;

    @Test
    public void getByIdTest() {
        Meeting meeting= meetingDao.getById(1L);
        Assert.assertNotNull(meeting);
    }

    @Test
    public void getAllTest() {
        List<Meeting> meetings = meetingDao.getAll();
        Assert.assertNotNull(meetings);
    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() {
        Meeting meeting = meetingDao.getById(1L);
        meeting.setName("newName");
        Assert.assertEquals(1, meetingDao.updateMeeting(meeting));
        Assert.assertEquals(meeting.getName(), meetingDao.getById(meeting.getId()).getName());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteTest() {
        Meeting meeting= meetingDao.getById(1L);
        Assert.assertEquals(1, meetingDao.deleteMeeting(meeting));
    }

    @Test
    @Transactional
    @Rollback
    public void createTest() {
        Group group = groupDao.getById(7L);
        Meeting meeting = new MeetingImpl("newName", new Timestamp(1L),"newPlace");
        meeting.setGroup(group);
        Assert.assertEquals(1, meetingDao.createMeeting(meeting));
    }
}
