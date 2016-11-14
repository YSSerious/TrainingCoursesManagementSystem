package ua.ukma.nc.dto;

import java.util.ArrayList;
import java.util.List;

import ua.ukma.nc.entity.Role;
import ua.ukma.nc.entity.User;

public class UserDto {

	private Long id;
	private String email;
	private String firstName;
	private String secondName;
	private String lastName;
	private boolean isActive;
	private List<String> roles;
	private String statusTitle;
	private String statusDescription;
	private Long statusId;

	public UserDto() {

	}

	public UserDto(User user) {
		setId(user.getId());
		setEmail(user.getEmail());
		setFirstName(user.getFirstName());
		setSecondName(user.getSecondName());
		setLastName(user.getLastName());
		setActive(user.isActive());

		if (user.getStudentStatus().getStatus().getId() != 0) {
			setStatusId(user.getStudentStatus().getStatus().getId());
			setStatusTitle(user.getStudentStatus().getStatus().getTitle());
			setStatusDescription(user.getStudentStatus().getStatus().getDescription());
		}
		
		List<String> roles = new ArrayList<>();
		for (Role role : user.getRoles())
			roles.add(convert(role.getTitle()));

		setRoles(roles);
	}

	private String convert(String title) {
		if (title.equals("ROLE_STUDENT"))
			return "Student";
		else if (title.equals("ROLE_MENTOR"))
			return "Mentor";
		else if (title.equals("ROLE_ADMIN"))
			return "Admin";
		else if (title.equals("ROLE_HR"))
			return "HR";
		return title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "UserImpl{" + "id=" + id + ", email='" + email + '\'' + ", firstName='" + firstName + '\''
				+ ", secondName='" + secondName + '\'' + ", lastName='" + lastName + '\'' + '\'' + ", isActive="
				+ isActive + ", roles=" + roles + '}';
	}

	public String getStatusTitle() {
		return statusTitle;
	}

	public void setStatusTitle(String statusTitle) {
		this.statusTitle = statusTitle;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}
}
