package ua.ukma.nc.entity.impl.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.Meeting;
import ua.ukma.nc.entity.impl.real.MeetingImpl;
import ua.ukma.nc.service.MeetingService;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public class MeetingProxy implements Meeting {

    private static final long serialVersionUID = -8565849301579695585L;
    private Long id;
    private MeetingImpl meeting;
    @Autowired
    private MeetingService meetingService;

    public MeetingProxy() {
    }

    public MeetingProxy(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Group getGroup() {
        downloadMeeting();
        return meeting.getGroup();
    }

    @Override
    public void setGroup(Group group) {
        downloadMeeting();
        meeting.setGroup(group);
    }

    @Override
    public String getName() {
        downloadMeeting();
        return meeting.getName();
    }

    @Override
    public void setName(String name) {
        downloadMeeting();
        meeting.setName(name);
    }

    @Override
    public Timestamp getTime() {
        downloadMeeting();
        return meeting.getTime();
    }

    @Override
    public void setTime(Timestamp time) {
        downloadMeeting();
        meeting.setTime(time);
    }

    @Override
    public String getPlace() {
        downloadMeeting();
        return meeting.getPlace();
    }

    @Override
    public void setPlace(String place) {
        downloadMeeting();
        meeting.setPlace(place);
    }

    @Override
    public List<Criterion> getCriterions() {
        downloadMeeting();
        return meeting.getCriterions();
    }

    @Override
    public void setCriterions(List<Criterion> criterions) {
        downloadMeeting();
        meeting.setCriterions(criterions);
    }

    private void downloadMeeting() {
        if (meeting == null) {
            meeting = (MeetingImpl) meetingService.getById(id);
        }
    }

    @Override
    public String toString() {
        return "Proxy " + id;
    }
}
