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
		MarkTableDto markTableDto = new MarkTableDto();
		
		List<String> meetingNames = new ArrayList<String>();
		
		for(Meeting meeting: meetings)
			meetingNames.add(meeting.getName());
		
		markTableDto.setMeetings(meetingNames);
		
		List<Criterion> criteria = criterionService.getByProject(projectId);
		List<String> criterionNames = new ArrayList<String>();
		
		for(Criterion criterion: criteria)
			criterionNames.add(criterion.getTitle());
		
		markTableDto.setCriteria(criterionNames);
		
		String[][] dataTable = new String[criterionNames.size()][meetingNames.size()];
		
		for(int i = 0; i< criterionNames.size(); i++)
			for(int j = 0; j< meetingNames.size(); j++)
				dataTable[i][j] = "-";
		
		List<MarkInformation> marksInformation = meetingResultService.generateMarkInformation(studentId, projectId);
		
		markTableDto.setTableData(dataTable);
		
		for(MarkInformation markInformation: marksInformation){
			int xIndex = criterionNames.indexOf(markInformation.getCriterionName());
			int yIndex = meetingNames.indexOf(markInformation.getMeetingName());
			
			dataTable[xIndex][yIndex] = String.valueOf(markInformation.getMark());
		}
		return markTableDto;
	}

}
