package ua.ukma.nc.dto;

import java.util.List;

public class AttendanceTable {
	private List<MeetingDto> meetings;
	
	private List<Attendance> attendance;

	public List<MeetingDto> getMeetings() {
		return meetings;
	}

	public void setMeetings(List<MeetingDto> meetings) {
		this.meetings = meetings;
	}

	public List<Attendance> getAttendance() {
		return attendance;
	}

	public void setAttendance(List<Attendance> attendance) {
		this.attendance = attendance;
	}

	@Override
	public String toString() {
		return "AttendanceTable [meetings=" + meetings + ", attendance=" + attendance + "]";
	}
}
