package ua.ukma.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ukma.nc.dao.GroupDao;
import ua.ukma.nc.dto.Attendance;
import ua.ukma.nc.dto.AttendanceTable;
import ua.ukma.nc.dto.MeetingDto;
import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.Meeting;
import ua.ukma.nc.entity.MeetingReview;
import ua.ukma.nc.entity.StatusLog;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.service.GroupService;
import ua.ukma.nc.service.MeetingReviewService;
import ua.ukma.nc.service.MeetingService;
import ua.ukma.nc.service.StatusLogService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Service
public class GroupServiceImpl implements GroupService{

    @Autowired
    private GroupDao groupDao;
    
    @Autowired
    private StatusLogService statusLogService;
    
    @Autowired
    private MeetingService meetingService;
    
    @Autowired
    private MeetingReviewService meetingReviewService;

    @Override
    public Group getById(Long id) {
        return groupDao.getById(id);
    }

    @Override
    public Group getByName(String name) {
        return groupDao.getByName(name);
    }

    @Override
    public List<Group> getByNames(List<String> names) {
        List<Group> groups = new ArrayList<>();
        for(String name:names)groups.add(groupDao.getByName(name));
        return groups;
    }

    @Override
    public Group getByUserProject(Long userId, Long projectId) {
        return groupDao.getByUserProject(userId, projectId);
    }

    @Override
    public int deleteGroup(Group group) {
        return groupDao.deleteGroup(group);
    }

    @Override
    public int updateGroup(Group group) {
        return groupDao.updateGroup(group);
    }

    @Override
    public List<Group> getAll() {
        return groupDao.getAll();
    }

    @Override
    public int createGroup(Group group) {
        return groupDao.createGroup(group);
    }

	@Override
	public List<Group> getByProjectId(Long projectId) {
		return groupDao.getByProjectId(projectId);
	}

	@Override
	public List<User> getMentors(Long groupId) {
		return groupDao.getMentors(groupId);
	}

	@Override
	public List<User> getStudents(Long groupId) {
		return groupDao.getStudents(groupId);
	}

        @Override
        public Long getStudentsAmount(Long groupId) {
            return groupDao.getStudentsAmount(groupId);
        }
        
	@Override
	public int removeMentor(Long groupId, Long userId) {
		return groupDao.removeMentor(groupId, userId);
	}

	@Override
	public int removeStudent(Long groupId, Long userId) {
		return groupDao.removeStudent(groupId, userId);
	}

	@Override
	public AttendanceTable getAttendaceTable(Long groupId) {

		AttendanceTable attendanceTable = new AttendanceTable();
		attendanceTable.setAttendance(new ArrayList<Attendance>());
		
		List<Meeting> meetings = meetingService.getByGroup(groupId);
		List<MeetingDto> meetingsDto = new ArrayList<MeetingDto>();
		
		attendanceTable.setMeetings(meetingsDto);
		
		for(Meeting meeting: meetings){
			MeetingDto meetingDto = new MeetingDto();
			meetingDto.setName(meeting.getName());
			meetingDto.setPlace(meeting.getPlace());
			meetingDto.setId(meeting.getId());
			meetingDto.setTime(meeting.getTime());
			
			meetingsDto.add(meetingDto);
		}
		
		Long projectId = getById(groupId).getProject().getId();
		
		List<User> students = getStudents(groupId);
		
		for(User student: students){
			Attendance attendance = new Attendance();
			attendance.setFullName(student.getLastName() + " " + student.getFirstName());
			attendance.setId(student.getId());
			
			List<String> attendanceHistory = new ArrayList<>();
			
			StatusLog last = statusLogService.getLast(groupId, student.getId());
			
			if(last.getNewStatus().getId() == 1){
				for(int i =0; i< meetingsDto.size(); i++)
					attendanceHistory.add("L");
				
				attendance.setAttendance(attendanceHistory);
				attendanceTable.getAttendance().add(attendance);
				
				continue;
			}
			
			for(int i =0; i< meetingsDto.size(); i++)
				attendanceHistory.add("");
			
			List<MeetingReview> meetingReviews = meetingReviewService.getByProjectStudent(projectId, student.getId());
			
			for(MeetingReview meetingReview: meetingReviews){
				Long meetingId = meetingReview.getMeeting().getId();
				int index = findMeeting(meetingId, meetingsDto);
				
				attendanceHistory.set(index, meetingReview.getType());
			}
			
			attendance.setAttendance(attendanceHistory);
			attendanceTable.getAttendance().add(attendance);
		}
		
		return attendanceTable;
	}
	
	private int findMeeting(Long id, List<MeetingDto> meetings){
		for(int i=0; i<meetings.size(); i++)
			if(meetings.get(i).getId() == id)
				return i;
		return -1;
	}
}
