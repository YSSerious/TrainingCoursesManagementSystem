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

	public UserDto() {

	}

	public UserDto(User user) {
		setId(user.getId());
		setEmail(user.getEmail());
		setFirstName(user.getFirstName());
		setSecondName(user.getSecondName());
		setLastName(user.getLastName());
		setActive(user.isActive());

		List<String> roles = new ArrayList<>();
		for (Role role : user.getRoles())
			roles.add(role.getTitle());

		setRoles(roles);
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
}
