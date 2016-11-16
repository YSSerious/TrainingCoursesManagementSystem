package ua.ukma.nc.entity;

import java.io.Serializable;

/**
 * Created by Алексей on 30.10.2016.
 */
public class MeetingResult implements Serializable{

    private static final long serialVersionUID = -5724258821238426944L;
    private Long id;
    private Criterion criterion;
    private MeetingReview meetingReview;
    private Mark mark;
    private String commentary;

    public MeetingResult(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Criterion getCriterion() {
        return criterion;
    }

    public void setCriterion(Criterion criterion) {
        this.criterion = criterion;
    }

    public MeetingReview getMeetingReview() {
        return meetingReview;
    }

    public void setMeetingReview(MeetingReview meetingReview) {
        this.meetingReview = meetingReview;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
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

        MeetingResult that = (MeetingResult) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (criterion != null ? !criterion.equals(that.criterion) : that.criterion != null) return false;
        if (meetingReview != null ? !meetingReview.equals(that.meetingReview) : that.meetingReview != null)
            return false;
        if (mark != null ? !mark.equals(that.mark) : that.mark != null) return false;
        return commentary != null ? commentary.equals(that.commentary) : that.commentary == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (criterion != null ? criterion.hashCode() : 0);
        result = 31 * result + (meetingReview != null ? meetingReview.hashCode() : 0);
        result = 31 * result + (mark != null ? mark.hashCode() : 0);
        result = 31 * result + (commentary != null ? commentary.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MeetingResult{" +
                "id=" + id +
                ", criterion=" + criterion +
                ", meetingReview=" + meetingReview +
                ", mark=" + mark +
                ", commentary='" + commentary + '\'' +
                '}';
    }
}
