package ua.ukma.nc.dto;

import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.Meeting;

/**
 * GroupProjectDto class is a DTO for groups which is listed on the project page. 
 * @author michael
 */
public class GroupProjectDto {
    
    Long id;
    String name;
    Long projectId;
    Meeting upcomingMeeting;
    Long studentsAmount;

    public GroupProjectDto(Group group, Meeting upcomingMeeting, Long studentsAmount) {
        this.id = group.getId();
        this.name = group.getName();
        this.projectId = group.getProject().getId();
        this.upcomingMeeting = upcomingMeeting;
        this.studentsAmount = studentsAmount;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Meeting getUpcomingMeeting() {
        return upcomingMeeting;
    }

    public void setUpcomingMeeting(Meeting upcomingMeeting) {
        this.upcomingMeeting = upcomingMeeting;
    }

    public Long getStudentsAmount() {
        return studentsAmount;
    }

    public void setStudentsAmount(Long studentsAmount) {
        this.studentsAmount = studentsAmount;
    }

    @Override
    public String toString() {
        return "GroupProjectDto{" + "id=" + id + ", name=" + name + ", projectId=" + projectId + ", upcomingMeeting=" + upcomingMeeting + ", studentsAmount=" + studentsAmount + '}';
    }
    
}
