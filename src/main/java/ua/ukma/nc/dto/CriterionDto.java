package ua.ukma.nc.dto;

import ua.ukma.nc.entity.Criterion;

public class CriterionDto implements Comparable<CriterionDto>{
	private Long id;
	private String title;
	private Long categoryId;
	private boolean rated;

	public CriterionDto() {

	}

	public CriterionDto(Criterion criterion) {
		setTitle(criterion.getTitle());
		setId(criterion.getId());
	}

	public CriterionDto(Long id, String title, boolean rated) {
		this.id = id;
		this.title = title;
		this.rated = rated;
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

	public boolean isRated() {
		return rated;
	}

	public void setRated(boolean rated) {
		this.rated = rated;
	}

	@Override
	public int compareTo(CriterionDto o) {
		return getTitle().compareToIgnoreCase(o.getTitle());
	}
}
