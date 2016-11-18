package ua.ukma.nc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.ukma.nc.dto.CertainMarkDto;
import ua.ukma.nc.dto.CriterionResult;
import ua.ukma.nc.dto.MarkInformation;
import ua.ukma.nc.dto.MarkTableDto;
import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.FinalReview;
import ua.ukma.nc.entity.FinalReviewCriterion;
import ua.ukma.nc.entity.Meeting;
import ua.ukma.nc.service.CriterionService;
import ua.ukma.nc.service.FinalReviewCriterionService;
import ua.ukma.nc.service.FinalReviewService;
import ua.ukma.nc.service.MarkTableService;
import ua.ukma.nc.service.MeetingService;

@Service
public class MarkTableServiceImpl implements MarkTableService {

	@Autowired
	private FinalReviewCriterionService finalReviewCriterionService;

	@Autowired
	private FinalReviewService finalReviewService;

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

		meetingNames.add("Final");

		markTableDto.setMeetings(meetingNames);

		Map<String, List<String>> categories = new TreeMap<String, List<String>>();

		for (Criterion criterion : criteria) {
			String categoryId = criterion.getCategory().getName();
			List<String> currentList = categories.get(categoryId);

			if (currentList == null) {
				List<String> newList = new ArrayList<String>();
				newList.add(criterion.getTitle());
				categories.put(categoryId, newList);
			} else
				currentList.add(criterion.getTitle());

		}

		Map<String, List<CriterionResult>> dataTable = new TreeMap<String, List<CriterionResult>>();
		boolean leave = true;

		FinalReview finalReview = null;

		try {
			finalReview = finalReviewService.getByStudent(projectId, studentId, "L");
		} catch (Exception e) {
			leave = false;
		}

		for (String key : categories.keySet()) {
			List<CriterionResult> tempData = new ArrayList<CriterionResult>();

			for (int i = 0; i < categories.get(key).size(); i++) {
				CriterionResult criterionResult = new CriterionResult();
				criterionResult.setCriterionName(categories.get(key).get(i));
				criterionResult.setMarks(new ArrayList<CertainMarkDto>());
				tempData.add(criterionResult);

				for (int j = 0; j < meetingId.size(); j++) {
					CertainMarkDto certainMarkDto = new CertainMarkDto();
					certainMarkDto.setValue("-");
					certainMarkDto.setCommentary("");
					certainMarkDto.setDescription("Okay. Maybe next time.");

					criterionResult.getMarks().add(certainMarkDto);
				}

				if (!leave) {
					CertainMarkDto certainMarkDto = new CertainMarkDto();
					certainMarkDto.setValue("U");
					certainMarkDto.setCommentary("");
					certainMarkDto.setDescription("No final review :c");

					criterionResult.getMarks().add(certainMarkDto);
				} else {
					CertainMarkDto certainMarkDto = new CertainMarkDto();
					certainMarkDto.setValue("L");
					certainMarkDto.setCommentary("");
					certainMarkDto.setDescription("Brrr...");

					criterionResult.getMarks().add(certainMarkDto);
				}
			}

			dataTable.put(key, tempData);
		}

		markTableDto.setTableData(dataTable);

		putMarks(meetings, categories, meetingId, dataTable, "U", "Maybe next time");
		putMarks(absentMeetings, categories, meetingId, dataTable, "A", "Very bad...");
		putMarks(leaveMeetings, categories, meetingId, dataTable, "L", "Very-very bad...");

		for (MarkInformation markInformation : marksInformation) {

			String categoryName = markInformation.getCategory();

			int criterionIndex = categories.get(categoryName).indexOf(markInformation.getCriterionName());
			int meetingIndex = meetingId.indexOf(markInformation.getMeetingId());

			CertainMarkDto certainMarkDto = new CertainMarkDto();
			certainMarkDto.setCommentary(markInformation.getCommentary());
			certainMarkDto.setDescription(markInformation.getMarkDescription());
			certainMarkDto.setValue(String.valueOf(markInformation.getMark()));

			dataTable.get(categoryName).get(criterionIndex).getMarks().set(meetingIndex, certainMarkDto);
		}

		if(finalReviewService.existsForProject(studentId, projectId, "F")) {
			finalReview = finalReviewService.getByStudent(projectId, studentId, "F");
			List<FinalReviewCriterion> reviews = finalReviewCriterionService.getByFinalReview(finalReview.getId());

			for (FinalReviewCriterion review : reviews) {
				String criterionName = review.getCriterion().getTitle();
				String categoryName = review.getCriterion().getCategory().getName();

				int criterionIndex = categories.get(categoryName).indexOf(criterionName);
				int meetingIndex = meetingId.size() - 1;

				CertainMarkDto certainMarkDto = new CertainMarkDto();
				certainMarkDto.setValue(String.valueOf(review.getMark().getValue()));
				certainMarkDto.setCommentary(review.getCommentary());
				certainMarkDto.setDescription(review.getMark().getDescription());

				dataTable.get(categoryName).get(criterionIndex).getMarks().set(meetingIndex, certainMarkDto);
			}
		}
		
		
		return markTableDto;
	}

	private void putMarks(List<Meeting> meetings, Map<String, List<String>> categories, List<Long> meetingId,
			Map<String, List<CriterionResult>> dataTable, String value, String description) {
		for (Meeting meeting : meetings) {
			List<Criterion> currentCriteria = meeting.getCriterions();
			for (Criterion currentCriterion : currentCriteria) {

				String criterionName = currentCriterion.getTitle();
				Long currentMeetingId = meeting.getId();
				String categoryName = currentCriterion.getCategory().getName();

				int criterionIndex = categories.get(categoryName).indexOf(criterionName);
				int meetingIndex = meetingId.indexOf(currentMeetingId);

				CertainMarkDto certainMarkDto = new CertainMarkDto();
				certainMarkDto.setValue(value);
				certainMarkDto.setCommentary("");
				certainMarkDto.setDescription(description);

				dataTable.get(categoryName).get(criterionIndex).getMarks().set(meetingIndex, certainMarkDto);
			}
		}
	}

}
