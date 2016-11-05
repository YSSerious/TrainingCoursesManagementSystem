package ua.ukma.nc.entity.impl.real;

import ua.ukma.nc.entity.Meeting;
import ua.ukma.nc.entity.MeetingReview;
import ua.ukma.nc.entity.User;

/**
 * Created by Алексей on 30.10.2016.
 */
public class MeetingReviewImpl implements MeetingReview{

    private static final long serialVersionUID = -1896122238338639738L;
    private Long id;
    private User student;
    private Meeting meeting;
    private User mentor;
    private String type;
    private String commentary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public User getMentor() {
        return mentor;
    }

    public void setMentor(User mentor) {
        this.mentor = mentor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeetingReviewImpl that = (MeetingReviewImpl) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (student != null ? !student.equals(that.student) : that.student != null) return false;
        if (meeting != null ? !meeting.equals(that.meeting) : that.meeting != null) return false;
        if (mentor != null ? !mentor.equals(that.mentor) : that.mentor != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        return commentary != null ? commentary.equals(that.commentary) : that.commentary == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (student != null ? student.hashCode() : 0);
        result = 31 * result + (meeting != null ? meeting.hashCode() : 0);
        result = 31 * result + (mentor != null ? mentor.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (commentary != null ? commentary.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MeetingReviewImpl{" +
                "id=" + id +
                ", student=" + student +
                ", meeting=" + meeting +
                ", mentor=" + mentor +
                ", type='" + type + '\'' +
                ", commentary='" + commentary + '\'' +
                '}';
    }
}
