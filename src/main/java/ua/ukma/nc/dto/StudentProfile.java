package ua.ukma.nc.dto;

import java.util.List;

public class StudentProfile {
	
	private String firstName;
	private String lastName;
	private String secondName;
	
	private String projectName;

	private List<StudentStatusLog> studentStatuses;
	private List<StudentMeetingReview> meetingReviews;

	private MarkTableDto markTableDto;

	private List<CategoryChartDto> chartInfo;
	private List<CategoryChartDto> chartInfoFinal;
	
	private FinalReviewDto generalReview;
	private FinalReviewDto technicalReview;

	public List<StudentStatusLog> getStudentStatuses() {
		return studentStatuses;
	}

	public void setStudentStatuses(List<StudentStatusLog> studentStatuses) {
		this.studentStatuses = studentStatuses;
	}

	public List<StudentMeetingReview> getMeetingReviews() {
		return meetingReviews;
	}

	public void setMeetingReviews(List<StudentMeetingReview> meetingReviews) {
		this.meetingReviews = meetingReviews;
	}

	public MarkTableDto getMarkTableDto() {
		return markTableDto;
	}

	public void setMarkTableDto(MarkTableDto markTableDto) {
		this.markTableDto = markTableDto;
	}

	public List<CategoryChartDto> getChartInfo() {
		return chartInfo;
	}

	public void setChartInfo(List<CategoryChartDto> chartInfo) {
		this.chartInfo = chartInfo;
	}

	public List<CategoryChartDto> getChartInfoFinal() {
		return chartInfoFinal;
	}

	public void setChartInfoFinal(List<CategoryChartDto> chartInfoFinal) {
		this.chartInfoFinal = chartInfoFinal;
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

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public FinalReviewDto getGeneralReview() {
		return generalReview;
	}

	public void setGeneralReview(FinalReviewDto generalReview) {
		this.generalReview = generalReview;
	}

	public FinalReviewDto getTechnicalReview() {
		return technicalReview;
	}

	public void setTechnicalReview(FinalReviewDto technicalReview) {
		this.technicalReview = technicalReview;
	}

}
