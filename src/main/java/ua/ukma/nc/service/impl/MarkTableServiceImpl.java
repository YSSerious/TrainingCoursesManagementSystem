package ua.ukma.nc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.ukma.nc.dto.CertainMarkDto;
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
	public MarkTableDto getMarkTableDto(Long studentId, Long projectId, List<MarkInformation> marksInformation) {

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

		Map<String, CertainMarkDto[][]> dataTable = new TreeMap<String, CertainMarkDto[][]>();

		for (String key : criterionNames.keySet()) {
			CertainMarkDto[][] tempData = new CertainMarkDto[criterionNames.get(key).size()][meetingNames.size() + 1];
			for (int i = 0; i < criterionNames.get(key).size(); i++) {
				tempData[i][0] = new CertainMarkDto();
				tempData[i][0].setValue(criterionNames.get(key).get(i));
				for (int j = 1; j < meetingNames.size() + 1; j++) {
					tempData[i][j] = new CertainMarkDto();
					tempData[i][j].setValue("-");
					tempData[i][j].setCommentary("");
					tempData[i][j].setDescription("Okay. Maybe next time.");
				}
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

				dataTable.get(categoryName)[xIndex][yIndex + 1] = new CertainMarkDto();
				dataTable.get(categoryName)[xIndex][yIndex + 1].setValue("U");
				dataTable.get(categoryName)[xIndex][yIndex + 1].setCommentary("");
				dataTable.get(categoryName)[xIndex][yIndex + 1].setDescription("Maybe next time...");
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

				dataTable.get(categoryName)[xIndex][yIndex + 1] = new CertainMarkDto();
				dataTable.get(categoryName)[xIndex][yIndex + 1].setValue("A");
				dataTable.get(categoryName)[xIndex][yIndex + 1].setCommentary("");
				dataTable.get(categoryName)[xIndex][yIndex + 1].setDescription("Very-very bad...");

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

				dataTable.get(categoryName)[xIndex][yIndex + 1] = new CertainMarkDto();
				dataTable.get(categoryName)[xIndex][yIndex + 1].setValue("L");
				dataTable.get(categoryName)[xIndex][yIndex + 1].setCommentary("");
				dataTable.get(categoryName)[xIndex][yIndex + 1].setDescription("That's all...");

			}
		}

		for (MarkInformation markInformation : marksInformation) {

			String categoryName = markInformation.getCategory();

			int xIndex = criterionNames.get(categoryName).indexOf(markInformation.getCriterionName());
			int yIndex = meetingNames.indexOf(markInformation.getMeetingName());

			dataTable.get(categoryName)[xIndex][yIndex + 1] = new CertainMarkDto();
			dataTable.get(categoryName)[xIndex][yIndex + 1].setCommentary(markInformation.getCommentary());
			dataTable.get(categoryName)[xIndex][yIndex + 1].setDescription(markInformation.getMarkDescription());
			dataTable.get(categoryName)[xIndex][yIndex + 1].setValue(String.valueOf(markInformation.getMark()));
		}
		return markTableDto;
	}

}
