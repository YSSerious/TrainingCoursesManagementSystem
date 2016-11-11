package ua.ukma.nc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.ukma.nc.dto.MarkInformation;
import ua.ukma.nc.dto.MarkTableDto;
import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.Meeting;
import ua.ukma.nc.service.CriterionService;
import ua.ukma.nc.service.MarkTableService;
import ua.ukma.nc.service.MeetingResultService;
import ua.ukma.nc.service.MeetingService;

@Service
public class MarkTableServiceImpl implements MarkTableService{
	
	@Autowired
	private MeetingService meetingService;
	
	@Autowired
	private CriterionService criterionService;
	
	@Autowired
	private MeetingResultService meetingResultService;

	@Override
	public MarkTableDto getMarkTableDto(Long studentId, Long projectId) {

		List<Meeting> meetings = meetingService.getByStudentProject(studentId, projectId);
		List<Meeting> absentMeetings = meetingService.getByStudentProjectType(studentId, projectId, 'A');
		List<Meeting> leaveMeetings = meetingService.getByStudentProjectType(studentId, projectId, 'L');
		
		MarkTableDto markTableDto = new MarkTableDto();
		
		List<Long> meetingId = new ArrayList<Long>();
		List<String> meetingNames = new ArrayList<String>();
		
		for(Meeting meeting: meetings){
			meetingNames.add(meeting.getName());
			meetingId.add(meeting.getId());
		}
		
		markTableDto.setMeetings(meetingNames);
		
		List<Long> criterionId = new ArrayList<Long>();
		List<Criterion> criteria = criterionService.getByProject(projectId);
		List<String> criterionNames = new ArrayList<String>();
		
		for(Criterion criterion: criteria){
			criterionNames.add(criterion.getTitle());
			criterionId.add(criterion.getId());
		}
		
		markTableDto.setCriteria(criterionNames);
		
		String[][] dataTable = new String[criterionNames.size()][meetingNames.size()];
		
		for(int i = 0; i< criterionNames.size(); i++)
			for(int j = 0; j< meetingNames.size(); j++)
				dataTable[i][j] = "-";
		
		List<MarkInformation> marksInformation = meetingResultService.generateMarkInformation(studentId, projectId);
		
		markTableDto.setTableData(dataTable);
		
		for(Meeting meeting: meetings){
			List<Criterion> ids = meeting.getCriterions();
			for(Criterion idCriterion: ids){
				Long checkCriterionId = idCriterion.getId();
				Long checMeetingId = meeting.getId();
				
				int xIndex = criterionId.indexOf(checkCriterionId);
				int yIndex = meetingId.indexOf(checMeetingId);
				
				dataTable[xIndex][yIndex] = "U";
			}
		}
		
		for(Meeting meeting: absentMeetings){
			List<Criterion> ids = meeting.getCriterions();
			for(Criterion idCriterion: ids){
				Long checkCriterionId = idCriterion.getId();
				Long checMeetingId = meeting.getId();
				
				int xIndex = criterionId.indexOf(checkCriterionId);
				int yIndex = meetingId.indexOf(checMeetingId);
				
				dataTable[xIndex][yIndex] = "A";
			}
		}
		
		for(Meeting meeting: leaveMeetings){
			List<Criterion> ids = meeting.getCriterions();
			for(Criterion idCriterion: ids){
				Long checkCriterionId = idCriterion.getId();
				Long checMeetingId = meeting.getId();
				
				int xIndex = criterionId.indexOf(checkCriterionId);
				int yIndex = meetingId.indexOf(checMeetingId);
				
				dataTable[xIndex][yIndex] = "L";
			}
		}
		
		for(MarkInformation markInformation: marksInformation){
			int xIndex = criterionNames.indexOf(markInformation.getCriterionName());
			int yIndex = meetingNames.indexOf(markInformation.getMeetingName());
			
			dataTable[xIndex][yIndex] = String.valueOf(markInformation.getMark());
		}
		return markTableDto;
	}

}
