package ua.ukma.nc.dto;

import java.util.List;

public class CategoryResult {
	private CategoryDto categoryDto;
	private List<CriterionResult> criteriaResults;

	public CategoryDto getCategoryDto() {
		return categoryDto;
	}

	public void setCategoryDto(CategoryDto categoryDto) {
		this.categoryDto = categoryDto;
	}

	public List<CriterionResult> getCriteriaResults() {
		return criteriaResults;
	}

	public void setCriteriaResults(List<CriterionResult> criteriaResults) {
		this.criteriaResults = criteriaResults;
	}
}
