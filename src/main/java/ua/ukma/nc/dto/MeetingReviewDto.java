package ua.ukma.nc.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import ua.ukma.nc.entity.Meeting;
import ua.ukma.nc.entity.MeetingReview;

public class MeetingReviewDto implements Comparable<MeetingReviewDto>{
	
	private String name;
	private Long id;
	private String type;
	private String commentary;
	
	private List<MeetingResultDto> marks;
	
	private String date;
	private Timestamp timestamp;
	
	public MeetingReviewDto(){
		
	}
	
	public MeetingReviewDto(MeetingReview meetingReview){
		Meeting meeting = meetingReview.getMeeting();
		type = meetingReview.getType();
		commentary = meetingReview.getCommentary();
		date = new SimpleDateFormat("MM/dd/yyyy").format(meeting.getTime());
		setName(meeting.getName());
		setId(meeting.getId());
		setTimestamp(meeting.getTime());
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<MeetingResultDto> getMarks() {
		return marks;
	}

	public void setMarks(List<MeetingResultDto> marks) {
		this.marks = marks;
	}

	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int compareTo(MeetingReviewDto meetingReviewDto) {
		return (-1) * getTimestamp().compareTo(meetingReviewDto.getTimestamp());
	}
}
