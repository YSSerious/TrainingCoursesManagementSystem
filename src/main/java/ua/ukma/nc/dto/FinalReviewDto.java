package ua.ukma.nc.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import ua.ukma.nc.entity.FinalReview;
import ua.ukma.nc.entity.User;

public class FinalReviewDto {

	private String firstName;
	private String lastName;
	private String secondName;

	private String type;
	private String commentary;
	private String date;
	
	private List<FinalReviewCriterionDto> marks;

	public FinalReviewDto() {

	}

	public FinalReviewDto(FinalReview finalReview) {
		User employee = finalReview.getEmployee();
		setFirstName(employee.getFirstName());
		setSecondName(employee.getSecondName());
		setLastName(employee.getLastName());

		setType(finalReview.getType());
		setCommentary(finalReview.getCommentary());
		setDate(finalReview.getDate());
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}

	public String getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = new SimpleDateFormat("MM/dd/yyyy").format(date);
	}

	public List<FinalReviewCriterionDto> getMarks() {
		return marks;
	}

	public void setMarks(List<FinalReviewCriterionDto> finalReviewCriteriaDto) {
		this.marks = finalReviewCriteriaDto;
	}
}
