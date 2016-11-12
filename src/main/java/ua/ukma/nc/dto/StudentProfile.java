package ua.ukma.nc.dto;

import java.util.List;
import java.util.Map;

public class StudentProfile {
	private Map<String, List<MarkInformation>> markInformation;
	private List<StudentStatusLog> studentStatuses;
	private List<StudentMeetingReview> meetingReviews;
	private MarkTableDto markTableDto;
	private Map<String, List<StudyResultDto>> chartInfo;

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

	public Map<String, List<StudyResultDto>> getChartInfo() {
		return chartInfo;
	}

	public void setChartInfo(Map<String, List<StudyResultDto>> chartInfo) {
		this.chartInfo = chartInfo;
	}
}
