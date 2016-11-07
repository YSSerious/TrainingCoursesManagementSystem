package ua.ukma.nc.dto;

import java.util.ArrayList;
import java.util.List;

import ua.ukma.nc.entity.Category;
import ua.ukma.nc.entity.Criterion;

public class CategoryDto {
	private Long id;
	private String name;
	private String description;
	private List<CriterionDto> criteria;

	public CategoryDto() {

	}

	public CategoryDto(Category category) {
		setId(category.getId());
		setName(category.getName());
		setDescription(category.getDescription());

		List<CriterionDto> criteria = new ArrayList<>();
		for (Criterion criterion : category.getCriteria())
			criteria.add(new CriterionDto(criterion));

		setCriteria(criteria);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<CriterionDto> getCriteria() {
		return criteria;
	}

	public void setCriteria(List<CriterionDto> criteria) {
		this.criteria = criteria;
	}
}
