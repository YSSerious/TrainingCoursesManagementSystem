package ua.ukma.nc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.ukma.nc.dto.CategoryDto;
import ua.ukma.nc.dto.CategoryResult;
import ua.ukma.nc.dto.CertainMarkDto;
import ua.ukma.nc.dto.CriterionResult;
import ua.ukma.nc.dto.MarkInformation;
import ua.ukma.nc.dto.MarkTableDto;
import ua.ukma.nc.entity.Category;
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

		Map<CategoryDto, List<Criterion>> categories = new TreeMap<CategoryDto, List<Criterion>>();

		for (Criterion criterion : criteria) {
			CategoryDto category = new CategoryDto();
			Category categoryEntity = criterion.getCategory();
			category.setId(categoryEntity.getId());
			category.setName(categoryEntity.getName());
			category.setDescription(categoryEntity.getDescription());
			
			List<Criterion> currentList = categories.get(category);

			if (currentList == null) {
				List<Criterion> newList = new ArrayList<Criterion>();
				newList.add(criterion);
				categories.put(category, newList);
			} else
				currentList.add(criterion);

		}

		Map<CategoryDto, List<CriterionResult>> dataTable = new TreeMap<CategoryDto, List<CriterionResult>>();
		boolean leave = true;

		FinalReview finalReview = null;

		try {
			finalReview = finalReviewService.getByStudent(projectId, studentId, "L");
		} catch (Exception e) {
			leave = false;
		}

		for (CategoryDto key : categories.keySet()) {
			List<CriterionResult> tempData = new ArrayList<CriterionResult>();

			for (int i = 0; i < categories.get(key).size(); i++) {
				CriterionResult criterionResult = new CriterionResult();
				criterionResult.setCriterionId(categories.get(key).get(i).getId());
				criterionResult.setCriterionName(categories.get(key).get(i).getTitle());
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
		
		List<CategoryResult> dataTableDto = new ArrayList<CategoryResult>();
		for(CategoryDto categoryDto: dataTable.keySet()){
			CategoryResult categoryResult = new CategoryResult();
			categoryResult.setCategoryDto(categoryDto);
			categoryResult.setCriteriaResults(dataTable.get(categoryDto));
			
			dataTableDto.add(categoryResult);
		}

		markTableDto.setTableData(dataTableDto);

		putMarks(meetings, categories, meetingId, dataTable, "U", "Maybe next time");
		putMarks(absentMeetings, categories, meetingId, dataTable, "A", "Very bad...");
		putMarks(leaveMeetings, categories, meetingId, dataTable, "L", "Very-very bad...");

		for (MarkInformation markInformation : marksInformation) {

			CategoryDto category = new CategoryDto();
			category.setName(markInformation.getCategory());

			int criterionIndex = find(categories.get(category),markInformation.getCriterionName());
			int meetingIndex = meetingId.indexOf(markInformation.getMeetingId());

			CertainMarkDto certainMarkDto = new CertainMarkDto();
			certainMarkDto.setCommentary(markInformation.getCommentary());
			certainMarkDto.setDescription(markInformation.getMarkDescription());
			certainMarkDto.setValue(String.valueOf(markInformation.getMark()));

			dataTable.get(category).get(criterionIndex).getMarks().set(meetingIndex, certainMarkDto);
		}

		if(finalReviewService.existsForProject(studentId, projectId, "F")) {
			finalReview = finalReviewService.getByStudent(projectId, studentId, "F");
			List<FinalReviewCriterion> reviews = finalReviewCriterionService.getByFinalReview(finalReview.getId());

			for (FinalReviewCriterion review : reviews) {
				String criterionName = review.getCriterion().getTitle();
				Category categoryEntity = review.getCriterion().getCategory();
				CategoryDto category = new CategoryDto();
				category.setId(categoryEntity.getId());
				category.setName(categoryEntity.getName());
				category.setDescription(categoryEntity.getDescription());
				
				int criterionIndex = find(categories.get(category),criterionName);
				int meetingIndex = meetingId.size() - 1;

				CertainMarkDto certainMarkDto = new CertainMarkDto();
				certainMarkDto.setValue(String.valueOf(review.getMark().getValue()));
				certainMarkDto.setCommentary(review.getCommentary());
				certainMarkDto.setDescription(review.getMark().getDescription());

				dataTable.get(category).get(criterionIndex).getMarks().set(meetingIndex, certainMarkDto);
			}
		}
		
		
		return markTableDto;
	}
	
	private int find(List<Criterion> criteria, String name){
		for(int i=0; i<criteria.size(); i++)
			if(criteria.get(i).getTitle().equals(name))
				return i;
		
		return -1;
	}

	private void putMarks(List<Meeting> meetings, Map<CategoryDto, List<Criterion>> categories, List<Long> meetingId,
			Map<CategoryDto, List<CriterionResult>> dataTable, String value, String description) {
		for (Meeting meeting : meetings) {
			List<Criterion> currentCriteria = meeting.getCriterions();
			for (Criterion currentCriterion : currentCriteria) {

				String criterionName = currentCriterion.getTitle();
				Long currentMeetingId = meeting.getId();
				Category categoryEntity = currentCriterion.getCategory();
				CategoryDto category = new CategoryDto();
				category.setId(categoryEntity.getId());
				category.setName(categoryEntity.getName());
				category.setDescription(categoryEntity.getDescription());

				int criterionIndex = find(categories.get(category),criterionName);
				int meetingIndex = meetingId.indexOf(currentMeetingId);

				CertainMarkDto certainMarkDto = new CertainMarkDto();
				certainMarkDto.setValue(value);
				certainMarkDto.setCommentary("");
				certainMarkDto.setDescription(description);

				dataTable.get(category).get(criterionIndex).getMarks().set(meetingIndex, certainMarkDto);
			}
		}
	}

}
