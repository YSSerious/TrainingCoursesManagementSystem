package ua.ukma.nc.dto;

import java.util.List;

public class CriterionResult {
	private String criterionName;
	private List<CertainMarkDto> marks;
	
	public String getCriterionName() {
		return criterionName;
	}
	public void setCriterionName(String criterionName) {
		this.criterionName = criterionName;
	}
	public List<CertainMarkDto> getMarks() {
		return marks;
	}
	public void setMarks(List<CertainMarkDto> marks) {
		this.marks = marks;
	}
}
