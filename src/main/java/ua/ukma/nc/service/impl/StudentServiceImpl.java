package ua.ukma.nc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.ukma.nc.dto.CategoryChartDto;
import ua.ukma.nc.dto.FinalReviewDto;
import ua.ukma.nc.dto.StudentMeetingReview;
import ua.ukma.nc.dto.StudentProfile;
import ua.ukma.nc.dto.StudentStatusLog;
import ua.ukma.nc.dto.StudyResultDto;
import ua.ukma.nc.entity.MeetingReview;
import ua.ukma.nc.entity.StatusLog;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.service.ChartService;
import ua.ukma.nc.service.FinalReviewService;
import ua.ukma.nc.service.MarkTableService;
import ua.ukma.nc.service.MeetingReviewService;
import ua.ukma.nc.service.ProjectService;
import ua.ukma.nc.service.StatusLogService;
import ua.ukma.nc.service.StudentService;
import ua.ukma.nc.service.UserService;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private ChartService chartService;

	@Autowired
	private StatusLogService statusLogService;

	@Autowired
	private MeetingReviewService meetingReviewService;

	@Autowired
	private MarkTableService markTableService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private FinalReviewService finalReviewService;

	@Override
	public StudentProfile generateStudentProfile(long studentId, long projectId) {

		StudentProfile studentProfile = new StudentProfile();

		prepareBasicInfo(studentProfile, projectId, studentId);
		studentProfile.setMarkTableDto(markTableService.getMarkTableDto(studentId, projectId));

		return studentProfile;
	}

	private void prepareBasicInfo(StudentProfile studentProfile, Long projectId, Long studentId) {
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

		studentProfile.setChartInfo(convert(chartService.getChartData(projectId, studentId)));
		studentProfile.setChartInfoFinal(convert(chartService.getChartDataFinalReview(projectId, studentId)));

		User user = userService.getById(studentId);
		studentProfile.setLastName(user.getLastName());
		studentProfile.setFirstName(user.getFirstName());
		studentProfile.setSecondName(user.getSecondName());
		studentProfile.setEmail(user.getEmail());

		studentProfile.setProjectName(projectService.getById(projectId).getName());

		if (finalReviewService.existsForProject(studentId, projectId, "G"))
			studentProfile
					.setGeneralReview(new FinalReviewDto(finalReviewService.getByStudent(projectId, studentId, "G")));

		if (finalReviewService.existsForProject(studentId, projectId, "T"))
			studentProfile
					.setTechnicalReview(new FinalReviewDto(finalReviewService.getByStudent(projectId, studentId, "T")));

	}

	private List<CategoryChartDto> convert(Map<String, List<StudyResultDto>> data) {
		List<CategoryChartDto> result = new ArrayList<CategoryChartDto>();

		for (String category : data.keySet()) {
			CategoryChartDto categoryChartDto = new CategoryChartDto();
			categoryChartDto.setCategory(category);
			categoryChartDto.setStudyResults(data.get(category));

			result.add(categoryChartDto);
		}
		return result;
	}

	@Override
	public StudentProfile generateStudentProfile(Long studentId, Long projectId, List<Long> criteria,
			List<Long> categories) {
		StudentProfile studentProfile = new StudentProfile();

		prepareBasicInfo(studentProfile, projectId, studentId);
		studentProfile.setMarkTableDto(markTableService.getMarkTableDto(studentId, projectId, criteria, categories));

		return studentProfile;
	}

}
