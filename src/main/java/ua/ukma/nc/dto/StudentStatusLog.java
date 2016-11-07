package ua.ukma.nc.dto;

import java.sql.Timestamp;

public class StudentStatusLog {
	private String statusDescription;
	private String commentary;
	private Long employeeId;
	private String firstName;
	private String lastName;
	private String secondName;
	private Timestamp time;
	
	public String getStatusDescription() {
		return statusDescription;
	}
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	public String getCommentary() {
		return commentary;
	}
	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
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
	public Timestamp getDate() {
		return time;
	}
	public void setDate(Timestamp time) {
		this.time = time;
	}
}
