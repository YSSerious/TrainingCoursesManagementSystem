package ua.ukma.nc.dto;

import java.util.ArrayList;
import java.util.List;

import ua.ukma.nc.entity.Category;
import ua.ukma.nc.entity.Criterion;

public class ProjectReportDto {
	private List<CriterionDto> criteria;
	private List<CategoryDto> categories;
	
	public ProjectReportDto(){
		
	}
	
	public ProjectReportDto(List<Criterion> criteria, List<Category> categories){
		this.criteria = new ArrayList<CriterionDto>();
		
		for(Criterion criterion: criteria)
			this.criteria.add(new CriterionDto(criterion));
		
		this.categories = new ArrayList<CategoryDto>();
		
		for(Category category: categories){
			CategoryDto categoryDto = new CategoryDto();
			categoryDto.setName(category.getName());
			categoryDto.setId(category.getId());
			
			this.categories.add(categoryDto);
		}
	}
	
	public List<CriterionDto> getCriteria() {
		return criteria;
	}
	public void setCriteria(List<CriterionDto> criteria) {
		this.criteria = criteria;
	}
	public List<CategoryDto> getCategories() {
		return categories;
	}
	public void setCategories(List<CategoryDto> categories) {
		this.categories = categories;
	}
}
