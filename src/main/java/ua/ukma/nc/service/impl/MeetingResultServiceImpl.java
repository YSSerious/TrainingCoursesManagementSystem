package ua.ukma.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ukma.nc.dao.MeetingResultDao;
import ua.ukma.nc.dto.MarkInformation;
import ua.ukma.nc.dto.StudentMeetingReview;
import ua.ukma.nc.dto.StudentProfile;
import ua.ukma.nc.dto.StudentStatusLog;
import ua.ukma.nc.entity.MeetingResult;
import ua.ukma.nc.entity.MeetingReview;
import ua.ukma.nc.entity.StatusLog;
import ua.ukma.nc.service.MeetingResultService;
import ua.ukma.nc.service.MeetingReviewService;
import ua.ukma.nc.service.StatusLogService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Алексей on 30.10.2016.
 */
@Service
public class MeetingResultServiceImpl implements MeetingResultService{
    @Autowired
    private MeetingResultDao meetingResultDao;
    
    @Autowired
    private StatusLogService statusLogService;
    
    @Autowired
    private MeetingReviewService meetingReviewService;
    
    @Override
    public MeetingResult getById(Long id) {
        return meetingResultDao.getById(id);
    }

    @Override
    public int deleteMeetingResult(MeetingResult meetingResult) {
        return meetingResultDao.deleteMeetingResult(meetingResult);
    }

    @Override
    public int updateMeetingResult(MeetingResult meetingResult) {
        return meetingResultDao.updateMeetingResult(meetingResult);
    }

    @Override
    public List<MeetingResult> getAll() {
        return meetingResultDao.getAll();
    }

    @Override
    public int createMeetingResult(MeetingResult meetingResult) {
        return meetingResultDao.createMeetingResult(meetingResult);
    }

	@Override
	public StudentProfile generateStudentProfile(long studentId, long projectId) {
		List<MarkInformation> allMarkInfo = meetingResultDao.generateMarkInformation(studentId, projectId);
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
			StudentStatusLog studentStatusLog = new StudentStatusLog();
			studentStatusLog.setCommentary(statusLog.getCommentary());
			studentStatusLog.setDate(statusLog.getDate());
			studentStatusLog.setEmployeeId(statusLog.getEmployee().getId());
			studentStatusLog.setStatusDescription(statusLog.getNewStatus().getDescription());
			studentStatusLog.setFirstName(statusLog.getEmployee().getFirstName());
			studentStatusLog.setLastName(statusLog.getEmployee().getLastName());
			studentStatusLog.setSecondName(statusLog.getEmployee().getSecondName());
			
			studentStatusLoges.add(studentStatusLog);
		}

		studentProfile.setStudentStatuses(studentStatusLoges);

		List<StudentMeetingReview> studentMeetingReviews = new ArrayList<StudentMeetingReview>();
		List<MeetingReview> meetingReviews = meetingReviewService.getByProjectStudent(projectId, studentId);

		for (MeetingReview meetingReview : meetingReviews) {
			StudentMeetingReview studentMeetingReview = new StudentMeetingReview();
			studentMeetingReview.setCommentary(meetingReview.getCommentary());
			studentMeetingReview.setMeetingName(meetingReview.getMeeting().getName());
			studentMeetingReview.setFirstName(meetingReview.getMentor().getFirstName());
			studentMeetingReview.setSecondName(meetingReview.getMentor().getSecondName());
			studentMeetingReview.setLastName(meetingReview.getMentor().getLastName());
			studentMeetingReview.setType(meetingReview.getType());
			
			studentMeetingReviews.add(studentMeetingReview);
		}

		studentProfile.setMeetingReviews(studentMeetingReviews);
		return studentProfile;
	}
}
