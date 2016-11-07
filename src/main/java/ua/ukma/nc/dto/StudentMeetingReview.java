package ua.ukma.nc.dto;

import ua.ukma.nc.entity.MeetingReview;

public class StudentMeetingReview {
	private String meetingName;
	private String firstName;
	private String lastName;
	private String secondName;

	private String type;
	private String commentary;
	
	public StudentMeetingReview(){
		
	}

	public StudentMeetingReview(MeetingReview meetingReview) {
		setCommentary(meetingReview.getCommentary());
		setMeetingName(meetingReview.getMeeting().getName());
		setFirstName(meetingReview.getMentor().getFirstName());
		setSecondName(meetingReview.getMentor().getSecondName());
		setLastName(meetingReview.getMentor().getLastName());
		setType(meetingReview.getType());
	}

	public String getMeetingName() {
		return meetingName;
	}

	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getType() {
		return type;
	}

	public void setType(String string) {
		this.type = string;
	}

	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}

}
