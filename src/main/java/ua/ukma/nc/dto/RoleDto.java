package ua.ukma.nc.dto;

import ua.ukma.nc.entity.Role;

public class RoleDto {
	private Long id;
	private String title;

	public RoleDto(Role role){
		this.setId(role.getId());
		this.setTitle(convert(role.getTitle()));
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
}
