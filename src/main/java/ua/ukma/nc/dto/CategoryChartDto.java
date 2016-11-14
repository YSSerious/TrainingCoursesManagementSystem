package ua.ukma.nc.dto;

import java.util.List;

public class CategoryChartDto {
	private String category;
	private List<StudyResultDto> studyResults;
	public List<StudyResultDto> getStudyResults() {
		return studyResults;
	}
	public void setStudyResults(List<StudyResultDto> studyResults) {
		this.studyResults = studyResults;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
}
