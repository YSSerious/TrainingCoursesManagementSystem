package ua.ukma.nc.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.Meeting;

public class MeetingDto {
	long id;
	GroupDto group;
	String name;
	Timestamp time;
	String place;
	List<CriterionDto> criterions;

	public MeetingDto() {

	}

	public MeetingDto(long id, String name, Timestamp time, String place) {
		this.id = id;
		this.name = name;
		this.time = time;
		this.place = place;
	}

	public MeetingDto(Meeting mt){
		setId(mt.getId());
		Group gr = mt.getGroup();
		setGroup(new GroupDto(gr));
		setName(mt.getName());
		setTime(mt.getTime());
		setPlace(mt.getPlace());
		ArrayList<CriterionDto> crit = new ArrayList<CriterionDto>();
		for (Criterion criterion : mt.getCriterions())
			crit.add(new CriterionDto(criterion));
		
		setCriterions(crit);

	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public GroupDto getGroup() {
		return group;
	}
	public void setGroup(GroupDto group) {
		this.group = group;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public List<CriterionDto> getCriterions() {
		return criterions;
	}
	public void setCriterions(List<CriterionDto> criterions) {
		this.criterions = criterions;
	}

}
