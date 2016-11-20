package ua.ukma.nc.dto;

import ua.ukma.nc.entity.MeetingResult;

public class MeetingResultDto {
	private String criterionName;
	private int value;
	private String description;
	private String commentary;
	
	public MeetingResultDto(){
		
	}
	
	public MeetingResultDto(MeetingResult meetingResult){
		criterionName = meetingResult.getCriterion().getTitle();
		value = meetingResult.getMark().getValue();
		commentary = meetingResult.getCommentary();
		description = meetingResult.getMark().getDescription();
	}
	
	public String getCriterionName() {
		return criterionName;
	}
	public void setCriterionName(String criterionName) {
		this.criterionName = criterionName;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}
}
