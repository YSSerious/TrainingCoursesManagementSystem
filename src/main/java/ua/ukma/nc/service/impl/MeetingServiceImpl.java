package ua.ukma.nc.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.ukma.nc.dao.GroupDao;
import ua.ukma.nc.dao.MeetingDao;
import ua.ukma.nc.dto.AddCriteriaDto;
import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.Meeting;
import ua.ukma.nc.service.CriterionService;
import ua.ukma.nc.service.GroupService;
import ua.ukma.nc.service.MeetingService;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Service
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    private MeetingDao meetingDao;
    @Autowired
    private GroupService groupService;
    @Autowired
    private CriterionService criterionService;

    @Override
    public Meeting getById(Long id) {
        return meetingDao.getById(id);
    }

    @Override
    public int deleteMeeting(Meeting meeting) {
        return meetingDao.deleteMeeting(meeting);
    }

    @Override
    public int updateMeeting(Meeting meeting) {
        return meetingDao.updateMeeting(meeting);
    }

    @Override
    public int editMeeting(Long id, String name, String date, String place) {
        return meetingDao.editMeeting(id, name, dateConverter(date), place);
    }

    @Override
    public List<Meeting> getAll() {
        return meetingDao.getAll();
    }

    @Override
    public int createMeeting(Meeting meeting) {
        return meetingDao.createMeeting(meeting);
    }

    @Override
    public int[] butchInsert(String name, String place, Timestamp date, List<Group> groups) {
        return meetingDao.butchInsert(name, place, date, groups);
    }

    @Override
    public List<Meeting> getByGroup(Long groupId) {
        return meetingDao.getByGroup(groupId);
    }

    @Override
    public Meeting getUpcomingByGroup(Long groupId) {
        return meetingDao.getUpcomingByGroup(groupId);
    }

    @Override
    public List<Meeting> getByStudentProject(Long studentId, Long projectId) {
        return meetingDao.getByStudentProject(studentId, projectId);
    }

    @Override
    public List<Meeting> getByStudentProjectType(Long studentId, Long projectId, Character type) {
        return meetingDao.getByStudentProjectType(studentId, projectId, type);
    }

    @Override
    public List<Meeting> getByNamePlaceDate(String name, String place, Timestamp date) {
        return meetingDao.getByNamePlaceDate(name, place, date);
    }

    @Override
    public List<Meeting> getWithoutReview(Long groupId, Long studentId) {
        return meetingDao.getWithoutReview(groupId, studentId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int addMeetings(AddCriteriaDto dto) {
        if(meetingDao.isExist(dateConverter(dto.getDate())))
            return 0;
        meetingDao.butchInsert(dto.getName(), dto.getPlace(), dateConverter(dto.getDate()),groupService.getByNames(dto.getGroups()));
        List<Meeting> createdMeetings = meetingDao.getByNamePlaceDate(dto.getName(), dto.getPlace(), dateConverter(dto.getDate()));
        for(Meeting meeting: createdMeetings){
            meetingDao.addMeetingCriterion(meeting, criterionService.getByNames(dto.getCriterions()));
        }
        return 1;
    }

    @Override
    public boolean isExist(Timestamp date) {
        return meetingDao.isExist(date);
    }

    @Override
    public Long getProjectByMeetingId(Long meetingId) {
        return meetingDao.getProjectByMeetingId(meetingId);
    }

    @Override
    public int addCriteria(Long meetingId, Criterion criterion) {
        return meetingDao.addCriteria(meetingId, criterion);
    }

    @Override
    public int deleteMeetingCriterion(Long meetingId, Criterion criterion) {
        return meetingDao.deleteMeetingCriterion(meetingId, criterion);
    }

    private Timestamp dateConverter(String dateTimeLocal){
        if (StringUtils.countMatches(dateTimeLocal, ":") == 1) {
            dateTimeLocal+=":00";
        }
        return Timestamp.valueOf(dateTimeLocal.replace("T"," "));
    }
}
