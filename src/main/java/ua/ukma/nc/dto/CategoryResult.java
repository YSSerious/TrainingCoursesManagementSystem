package ua.ukma.nc.dto;

import java.util.List;

public class CategoryResult implements Comparable<CategoryResult>{
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

	@Override
	public int compareTo(CategoryResult o) {
		return getCategoryDto().getName().compareTo(o.getCategoryDto().getName());
	}
}
