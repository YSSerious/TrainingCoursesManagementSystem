package ua.ukma.nc.entity;

import java.io.Serializable;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface MeetingReview extends Serializable{
     Long getId();

     void setId(Long id);

     User getStudent();

     void setStudent(User student);

     Meeting getMeeting();

     void setMeeting(Meeting meeting);

     User getMentor();

     void setMentor(User mentor);

     String getType();

     void setType(String type);

     String getCommentary();

     void setCommentary(String commentary);
}
