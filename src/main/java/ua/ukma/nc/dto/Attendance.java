package ua.ukma.nc.dto;

import java.util.List;

public class Attendance {

	private Long id;
	private String fullName;
	
	private List<String> attendance;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public List<String> getAttendance() {
		return attendance;
	}

	public void setAttendance(List<String> attendance) {
		this.attendance = attendance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Attendance [id=" + id + ", fullName=" + fullName + ", attendance=" + attendance + "]";
	}
}
