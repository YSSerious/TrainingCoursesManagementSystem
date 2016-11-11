package ua.ukma.nc.dto;

public class MarkInformation {

	private Long criterionId;
	private String criterionName;
	
	private String category;

	private int mark;
	private String markDescription;
	private String commentary;
	
	private Long meetingId;
	private String meetingName;

	public String getCriterionName() {
		return criterionName;
	}

	public void setCriterionName(String criterionName) {
		this.criterionName = criterionName;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public String getMarkDescription() {
		return markDescription;
	}

	public void setMarkDescription(String markDescription) {
		this.markDescription = markDescription;
	}
	
	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}

	public String getMeetingName() {
		return meetingName;
	}

	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getCriterionId() {
		return criterionId;
	}

	public void setCriterionId(Long criterionId) {
		this.criterionId = criterionId;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

}
