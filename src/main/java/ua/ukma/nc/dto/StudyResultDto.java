package ua.ukma.nc.dto;

public class StudyResultDto {
	private String criterionName;
	
	private float averageValue;
	
	public float getAverageValue() {
		return averageValue;
	}
	public void setAverageValue(float averageValue) {
		this.averageValue = averageValue;
	}
	public String getCriterionName() {
		return criterionName;
	}
	public void setCriterionName(String criterionName) {
		this.criterionName = criterionName;
	}
}
