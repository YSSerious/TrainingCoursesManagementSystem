package ua.ukma.nc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.ukma.nc.dto.MarkInformation;
import ua.ukma.nc.dto.StudentMeetingReview;
import ua.ukma.nc.dto.StudentProfile;
import ua.ukma.nc.dto.StudentStatusLog;
import ua.ukma.nc.entity.MeetingReview;
import ua.ukma.nc.entity.StatusLog;
import ua.ukma.nc.service.ChartService;
import ua.ukma.nc.service.MarkTableService;
import ua.ukma.nc.service.MeetingResultService;
import ua.ukma.nc.service.MeetingReviewService;
import ua.ukma.nc.service.StatusLogService;
import ua.ukma.nc.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private ChartService chartService;

	@Autowired
	private MeetingResultService meetingResultService;

	@Autowired
	private StatusLogService statusLogService;

	@Autowired
	private MeetingReviewService meetingReviewService;

	@Autowired
	private MarkTableService markTableService;

	@Override
	public StudentProfile generateStudentProfile(long studentId, long projectId) {
		List<MarkInformation> allMarkInfo = meetingResultService.generateMarkInformation(studentId, projectId);
		Map<String, List<MarkInformation>> separateInformation = new HashMap<String, List<MarkInformation>>();

		for (MarkInformation markInformation : allMarkInfo) {
			List<MarkInformation> currentInformation = separateInformation.get(markInformation.getCriterionName());
			if (currentInformation != null)
				currentInformation.add(markInformation);
			else {
				List<MarkInformation> newList = new ArrayList<MarkInformation>();
				newList.add(markInformation);
				separateInformation.put(markInformation.getCriterionName(), newList);
			}
		}

		StudentProfile studentProfile = new StudentProfile();
		studentProfile.setMarkInformation(separateInformation);

		List<StatusLog> statusLoges = statusLogService.getByProjectStudent(projectId, studentId);
		List<StudentStatusLog> studentStatusLoges = new ArrayList<StudentStatusLog>();

		for (StatusLog statusLog : statusLoges) {
			StudentStatusLog studentStatusLog = new StudentStatusLog(statusLog);
			studentStatusLoges.add(studentStatusLog);
		}

		studentProfile.setStudentStatuses(studentStatusLoges);

		List<StudentMeetingReview> studentMeetingReviews = new ArrayList<StudentMeetingReview>();
		List<MeetingReview> meetingReviews = meetingReviewService.getByProjectStudent(projectId, studentId);

		for (MeetingReview meetingReview : meetingReviews) {
			StudentMeetingReview studentMeetingReview = new StudentMeetingReview(meetingReview);
			studentMeetingReviews.add(studentMeetingReview);
		}

		studentProfile.setMeetingReviews(studentMeetingReviews);

		studentProfile.setMarkTableDto(markTableService.getMarkTableDto(studentId, projectId, allMarkInfo));
		
		studentProfile.setChartInfo(chartService.getChartData(projectId, studentId));
		return studentProfile;
	}

}
