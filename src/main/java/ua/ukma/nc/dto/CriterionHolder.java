package ua.ukma.nc.dto;

import java.util.List;

public class CriterionHolder {
	private String category;
	private List<Integer> marks;
	
	public List<Integer> getMarks() {
		return marks;
	}
	public void setMarks(List<Integer> marks) {
		this.marks = marks;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
}
