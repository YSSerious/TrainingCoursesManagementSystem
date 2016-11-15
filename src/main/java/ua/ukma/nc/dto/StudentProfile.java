package ua.ukma.nc.dto;

import java.util.List;
import java.util.Map;

public class StudentProfile {
	private Map<String, List<MarkInformation>> markInformation;
	private List<StudentStatusLog> studentStatuses;
	private List<StudentMeetingReview> meetingReviews;
	private MarkTableDto markTableDto;
	private List<CategoryChartDto> chartInfo;
	private List<CategoryChartDto> chartInfoFinal;

	public Map<String, List<MarkInformation>> getMarkInformation() {
		return markInformation;
	}

	public void setMarkInformation(Map<String, List<MarkInformation>> markInformation) {
		this.markInformation = markInformation;
	}

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
}
