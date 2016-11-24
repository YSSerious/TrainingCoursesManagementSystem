package ua.ukma.nc.dto;

import ua.ukma.nc.entity.FinalReviewCriterion;

public class FinalReviewCriterionDto {
	private String criterionName;
	private int value;
	private String description;
	private String commentary;
	
	public FinalReviewCriterionDto(){
		
	}
	
	public FinalReviewCriterionDto(FinalReviewCriterion finalReviewCriterion){
		criterionName = finalReviewCriterion.getCriterion().getTitle();
		value = finalReviewCriterion.getMark().getValue();
		commentary = finalReviewCriterion.getCommentary();
		description = finalReviewCriterion.getMark().getDescription();
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
