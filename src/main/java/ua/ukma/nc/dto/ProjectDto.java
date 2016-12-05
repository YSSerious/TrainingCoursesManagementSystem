package ua.ukma.nc.dto;

import java.sql.Date;
import java.util.Objects;

import ua.ukma.nc.entity.Project;

public class ProjectDto {
	long id;
	String name;
	String description;
	Date startDate;
	Date finishDate;
	public ProjectDto() {
		
	}
	public ProjectDto(Project pr){
		setId(pr.getId());
		setName(pr.getName());
		setDescription(pr.getDescription());
		setStartDate(pr.getStartDate());
		setFinishDate(pr.getFinishDate());
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ProjectDto other = (ProjectDto) obj;
		if (this.id != other.id) {
			return false;
		}
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		if (!Objects.equals(this.description, other.description)) {
			return false;
		}
		if (!Objects.equals(this.startDate, other.startDate)) {
			return false;
		}
		if (!Objects.equals(this.finishDate, other.finishDate)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "ProjectDto{" + "id=" + id + ", name=" + name + ", description=" + description + ", startDate=" + startDate + ", finishDate=" + finishDate + '}';
	}

}
