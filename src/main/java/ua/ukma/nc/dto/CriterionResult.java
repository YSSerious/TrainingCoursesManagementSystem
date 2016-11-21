package ua.ukma.nc.dto;

import java.util.List;

public class CriterionResult implements Comparable<CriterionResult>{
	private String criterionName;
	private Long criterionId;
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
	public Long getCriterionId() {
		return criterionId;
	}
	public void setCriterionId(Long criterionId) {
		this.criterionId = criterionId;
	}
	@Override
	public int compareTo(CriterionResult o) {
		return getCriterionName().compareTo(o.getCriterionName());
	}
}
