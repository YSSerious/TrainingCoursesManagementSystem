package ua.ukma.nc.dto;

import java.util.List;

public class MarkTableDto {
	
	private FinalReviewDto finalReview;
	private List<MeetingReviewDto> meetings;
	
	private List<CategoryResult> tableData;
	
	private List<CategoryDto> projectCategories;
	private List<CriterionDto> projectCriteria;

	public List<MeetingReviewDto> getMeetings() {
		return meetings;
	}

	public void setMeetings(List<MeetingReviewDto> meetings) {
		this.meetings = meetings;
	}

	public List<CategoryResult> getTableData() {
		return tableData;
	}

	public void setTableData(List<CategoryResult> dataTable) {
		this.tableData = dataTable;
	}

	public FinalReviewDto getFinalReview() {
		return finalReview;
	}

	public void setFinalReview(FinalReviewDto finalReview) {
		this.finalReview = finalReview;
	}

	public List<CategoryDto> getProjectCategories() {
		return projectCategories;
	}

	public void setProjectCategories(List<CategoryDto> projectCategories) {
		this.projectCategories = projectCategories;
	}

	public List<CriterionDto> getProjectCriteria() {
		return projectCriteria;
	}

	public void setProjectCriteria(List<CriterionDto> projectCriteria) {
		this.projectCriteria = projectCriteria;
	}
}
