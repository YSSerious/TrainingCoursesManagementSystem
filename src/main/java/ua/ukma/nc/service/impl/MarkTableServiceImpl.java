package ua.ukma.nc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.ukma.nc.dto.MarkInformation;
import ua.ukma.nc.dto.MarkTableDto;
import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.Meeting;
import ua.ukma.nc.service.CriterionService;
import ua.ukma.nc.service.MarkTableService;
import ua.ukma.nc.service.MeetingService;

@Service
public class MarkTableServiceImpl implements MarkTableService {

	@Autowired
	private MeetingService meetingService;

	@Autowired
	private CriterionService criterionService;

	@Override
	public MarkTableDto getMarkTableDto(Long studentId, Long projectId, List<MarkInformation> marksInformation ) {

		List<Meeting> meetings = meetingService.getByStudentProject(studentId, projectId);
		List<Meeting> absentMeetings = meetingService.getByStudentProjectType(studentId, projectId, 'A');
		List<Meeting> leaveMeetings = meetingService.getByStudentProjectType(studentId, projectId, 'L');
		List<Criterion> criteria = criterionService.getByProject(projectId);
		
		MarkTableDto markTableDto = new MarkTableDto();

		List<Long> meetingId = new ArrayList<Long>();
		List<String> meetingNames = new ArrayList<String>();

		for (Meeting meeting : meetings) {
			meetingNames.add(meeting.getName());
			meetingId.add(meeting.getId());
		}

		markTableDto.setMeetings(meetingNames);

		Map<String, List<Long>> criterionId = new TreeMap<String, List<Long>>();
		Map<String, List<String>> criterionNames = new TreeMap<String, List<String>>();

		for (Criterion criterion : criteria) {
			String categoryId = criterion.getCategory().getName();
			List<String> currentList = criterionNames.get(categoryId);

			if (currentList == null) {
				List<String> newList = new ArrayList<String>();
				newList.add(criterion.getTitle());
				criterionNames.put(categoryId, newList);
			} else
				currentList.add(criterion.getTitle());

			List<Long> ids = criterionId.get(categoryId);

			if (ids == null) {
				List<Long> newList = new ArrayList<Long>();
				newList.add(criterion.getId());

				criterionId.put(categoryId, newList);
			} else
				ids.add(criterion.getId());

		}

		Map<String, String[][]> dataTable = new TreeMap<String, String[][]>();

		for (String key : criterionNames.keySet()) {
			String[][] tempData = new String[criterionNames.get(key).size()][meetingNames.size() + 1];
			for (int i = 0; i < criterionNames.get(key).size(); i++) {
				tempData[i][0] = criterionNames.get(key).get(i);
				for (int j = 1; j < meetingNames.size() + 1; j++)
					tempData[i][j] = "-";
			}

			dataTable.put(key, tempData);
		}
		
		markTableDto.setTableData(dataTable);

		for (Meeting meeting : meetings) {
			List<Criterion> currentCriteria = meeting.getCriterions();
			for (Criterion currentCriterion : currentCriteria) {
				Long checkCriterionId = currentCriterion.getId();
				Long checMeetingId = meeting.getId();
				String categoryName = currentCriterion.getCategory().getName();

				int xIndex = criterionId.get(categoryName).indexOf(checkCriterionId);
				int yIndex = meetingId.indexOf(checMeetingId);

				dataTable.get(categoryName)[xIndex][yIndex + 1] = "U";
			}
		}

		for (Meeting meeting : absentMeetings) {
			List<Criterion> currentCriteria = meeting.getCriterions();
			for (Criterion currentCriterion : currentCriteria) {
				Long checkCriterionId = currentCriterion.getId();
				Long checMeetingId = meeting.getId();

				String categoryName = currentCriterion.getCategory().getName();

				int xIndex = criterionId.get(categoryName).indexOf(checkCriterionId);
				int yIndex = meetingId.indexOf(checMeetingId);

				dataTable.get(categoryName)[xIndex][yIndex + 1] = "A";
			}
		}

		for (Meeting meeting : leaveMeetings) {
			List<Criterion> currentCriteria = meeting.getCriterions();
			for (Criterion currentCriterion : currentCriteria) {
				Long checkCriterionId = currentCriterion.getId();
				Long checMeetingId = meeting.getId();

				String categoryName = currentCriterion.getCategory().getName();

				int xIndex = criterionId.get(categoryName).indexOf(checkCriterionId);
				int yIndex = meetingId.indexOf(checMeetingId);

				dataTable.get(categoryName)[xIndex][yIndex + 1] = "L";
			}
		}

		for (MarkInformation markInformation : marksInformation) {

			String categoryName = markInformation.getCategory();

			int xIndex = criterionNames.get(categoryName).indexOf(markInformation.getCriterionName());
			int yIndex = meetingNames.indexOf(markInformation.getMeetingName());

			dataTable.get(categoryName)[xIndex][yIndex] = String.valueOf(markInformation.getMark());
		}
		return markTableDto;
	}

}
