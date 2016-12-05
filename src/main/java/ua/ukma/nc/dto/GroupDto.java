package ua.ukma.nc.dto;

import java.util.ArrayList;
import java.util.List;

import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.User;

public class GroupDto {
	long id;
	ProjectDto project;
	String name;
	List<UserDto> users;

	public GroupDto() {

	}
	public GroupDto(Group gr){
		setId(gr.getId());
		setName(gr.getName());
		setProject(new ProjectDto(gr.getProject()));
	
	}
        
        public GroupDto(long id) {
            this.id = id;
        }
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public ProjectDto getProject() {
		return project;
	}
	public void setProject(ProjectDto project) {
		this.project = project;
	}
	
	public Long getProjectId() {
		if (this.project == null) {
			this.project = new ProjectDto();
		}
		return this.project.getId();
	}
	
	public void setProjectId(Long id) {
		if (this.project == null) {
			this.project = new ProjectDto();
		}
		this.project.setId(id);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<UserDto> getUsers() {
		return users;
	}
	public void setUsers(List<UserDto> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "GroupDto{" + "id=" + id + ", project=" + project + ", name=" + name + ", users=" + users + '}';
	}
	
}
