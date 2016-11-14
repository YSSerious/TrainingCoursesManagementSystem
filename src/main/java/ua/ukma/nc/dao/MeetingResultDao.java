package ua.ukma.nc.dao;

import ua.ukma.nc.dto.MarkInformation;
import ua.ukma.nc.entity.MeetingResult;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface MeetingResultDao {
    MeetingResult getById(Long id);

    int deleteMeetingResult(MeetingResult meetingResult);

    int updateMeetingResult(MeetingResult meetingResult);

    List<MeetingResult> getAll();

    int createMeetingResult(MeetingResult meetingResult);
    
    List<MarkInformation> generateMarkInformation(long studentId, long projectId);

    List<MeetingResult> getByReview(long review);
}
