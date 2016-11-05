package ua.ukma.nc.entity.impl.real;

import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.Meeting;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public class MeetingImpl implements Meeting{

    private static final long serialVersionUID = -5906155367928207710L;
    private Long id;
    private Group group;
    private String name;
    private Timestamp time;
    private String place;
    private List<Criterion> criterions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public List<Criterion> getCriterions() {
        return criterions;
    }

    public void setCriterions(List<Criterion> criterions) {
        this.criterions = criterions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeetingImpl meeting = (MeetingImpl) o;

        if (id != null ? !id.equals(meeting.id) : meeting.id != null) return false;
        if (group != null ? !group.equals(meeting.group) : meeting.group != null) return false;
        if (name != null ? !name.equals(meeting.name) : meeting.name != null) return false;
        if (time != null ? !time.equals(meeting.time) : meeting.time != null) return false;
        if (place != null ? !place.equals(meeting.place) : meeting.place != null) return false;
        return criterions != null ? criterions.equals(meeting.criterions) : meeting.criterions == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + (criterions != null ? criterions.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MeetingImpl{" +
                "id=" + id +
                ", group=" + group +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", place='" + place + '\'' +
                ", criterions=" + criterions +
                '}';
    }
}
