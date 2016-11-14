package ua.ukma.nc.dto;

import ua.ukma.nc.entity.Criterion;

public class CriterionDto {
	private Long id;
	private String title;
	private Long categoryId;

	public CriterionDto() {

	}

	public CriterionDto(Criterion criterion) {
		setTitle(criterion.getTitle());
		setId(criterion.getId());
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
}
