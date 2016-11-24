package ua.ukma.nc.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.ukma.nc.dto.CategoryDto;
import ua.ukma.nc.dto.CategoryResult;
import ua.ukma.nc.dto.CertainMarkDto;
import ua.ukma.nc.dto.CriterionResult;
import ua.ukma.nc.dto.FinalReviewCriterionDto;
import ua.ukma.nc.dto.FinalReviewDto;
import ua.ukma.nc.dto.MarkInformation;
import ua.ukma.nc.dto.MarkTableDto;
import ua.ukma.nc.dto.MeetingResultDto;
import ua.ukma.nc.dto.MeetingReviewDto;
import ua.ukma.nc.entity.Category;
import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.FinalReview;
import ua.ukma.nc.entity.FinalReviewCriterion;
import ua.ukma.nc.entity.Meeting;
import ua.ukma.nc.entity.MeetingResult;
import ua.ukma.nc.entity.MeetingReview;
import ua.ukma.nc.service.CriterionService;
import ua.ukma.nc.service.FinalReviewCriterionService;
import ua.ukma.nc.service.FinalReviewService;
import ua.ukma.nc.service.MarkTableService;
import ua.ukma.nc.service.MeetingResultService;
import ua.ukma.nc.service.MeetingReviewService;
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

	@Autowired
	private MeetingResultService meetingResultService;

	@Autowired
	private MeetingReviewService meetingReviewService;

	@Override
	public MarkTableDto getMarkTableDto(Long studentId, Long projectId) {
		List<MarkInformation> marksInformation = meetingResultService.generateMarkInformation(studentId, projectId);
		List<Meeting> meetings = meetingService.getByStudentProject(studentId, projectId);
		List<Criterion> criteria = criterionService.getByProject(projectId);

		MarkTableDto markTableDto = new MarkTableDto();

		List<Long> meetingId = new ArrayList<Long>();
		List<String> meetingNames = new ArrayList<String>();
		List<MeetingReviewDto> meetingReviewsDto = new ArrayList<MeetingReviewDto>();

		for (Meeting meeting : meetings) {
			String meetingName = meeting.getName();
			Long id = meeting.getId();

			meetingNames.add(meetingName);
			meetingId.add(id);

			MeetingReviewDto meetingReviewDto = new MeetingReviewDto();
			meetingReviewDto.setType("-");
			meetingReviewDto.setName(meetingName);
			meetingReviewDto.setId(id);
			meetingReviewDto.setTimestamp(meeting.getTime());

			MeetingReview meetingReview = meetingReviewService.getByMeetingStudent(id, studentId);

			if (meetingReview != null) {
				meetingReviewDto.setCommentary(meetingReview.getCommentary());
				meetingReviewDto.setType(meetingReview.getType());

				List<MeetingResultDto> meetingResultsDto = new ArrayList<MeetingResultDto>();

				for (MeetingResult meetingResult : meetingResultService.getByReview(meetingReview.getId())) {
					MeetingResultDto meetingResultDto = new MeetingResultDto(meetingResult);
					meetingResultsDto.add(meetingResultDto);
				}

				meetingReviewDto.setMarks(meetingResultsDto);
			}

			meetingReviewsDto.add(meetingReviewDto);
		}

		meetingNames.add("Final");

		markTableDto.setMeetings(meetingReviewsDto);

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
		boolean hasFinal = true;

		FinalReview finalReview = null;

		hasFinal = finalReviewService.existsForProject(studentId, projectId, "F");

		if (hasFinal) {
			finalReview = finalReviewService.getByStudent(projectId, studentId, "F");

			FinalReviewDto finalReviewDto = new FinalReviewDto(finalReview);
			List<FinalReviewCriterion> finalReviewCriteria = finalReviewCriterionService
					.getByFinalReview(finalReview.getId());

			List<FinalReviewCriterionDto> finalReviewCriteriaDto = new ArrayList<FinalReviewCriterionDto>();

			for (FinalReviewCriterion finalReviewCriterion : finalReviewCriteria) {
				FinalReviewCriterionDto finalReviewCriterionDto = new FinalReviewCriterionDto(finalReviewCriterion);
				finalReviewCriteriaDto.add(finalReviewCriterionDto);
			}

			finalReviewDto.setMarks(finalReviewCriteriaDto);

			markTableDto.setFinalReview(finalReviewDto);
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
					certainMarkDto.setValue(" ");
					certainMarkDto.setCommentary("");
					certainMarkDto.setDescription("No evaluation.");
					criterionResult.getMarks().add(certainMarkDto);
				}

				CertainMarkDto certainMarkDto = new CertainMarkDto();
				certainMarkDto.setValue("-");
				certainMarkDto.setCommentary("");
				certainMarkDto.setDescription("No final review.");

				criterionResult.getMarks().add(certainMarkDto);
			}

			dataTable.put(key, tempData);
		}

		List<CategoryResult> dataTableDto = new ArrayList<CategoryResult>();
		for (CategoryDto categoryDto : dataTable.keySet()) {
			CategoryResult categoryResult = new CategoryResult();
			categoryResult.setCategoryDto(categoryDto);
			categoryResult.setCriteriaResults(dataTable.get(categoryDto));

			dataTableDto.add(categoryResult);
		}

		markTableDto.setTableData(dataTableDto);

		putMarks(meetings, categories, meetingId, dataTable, "-", "No review.");

		for (MarkInformation markInformation : marksInformation) {

			CategoryDto category = new CategoryDto();
			category.setName(markInformation.getCategory());

			int criterionIndex = find(categories.get(category), markInformation.getCriterionName());
			int meetingIndex = meetingId.indexOf(markInformation.getMeetingId());

			CertainMarkDto certainMarkDto = new CertainMarkDto();
			certainMarkDto.setCommentary(markInformation.getCommentary());
			certainMarkDto.setDescription(markInformation.getMarkDescription());
			certainMarkDto.setValue(String.valueOf(markInformation.getMark()));

			dataTable.get(category).get(criterionIndex).getMarks().set(meetingIndex, certainMarkDto);
		}

		if (hasFinal) {
			List<FinalReviewCriterion> reviews = finalReviewCriterionService.getByFinalReview(finalReview.getId());

			for (FinalReviewCriterion review : reviews) {
				String criterionName = review.getCriterion().getTitle();
				Category categoryEntity = review.getCriterion().getCategory();
				CategoryDto category = new CategoryDto();
				category.setId(categoryEntity.getId());
				category.setName(categoryEntity.getName());
				category.setDescription(categoryEntity.getDescription());

				int criterionIndex = find(categories.get(category), criterionName);
				int meetingIndex = meetingId.size();

				CertainMarkDto certainMarkDto = new CertainMarkDto();
				certainMarkDto.setValue(String.valueOf(review.getMark().getValue()));
				certainMarkDto.setCommentary(review.getCommentary());
				certainMarkDto.setDescription(review.getMark().getDescription());

				dataTable.get(category).get(criterionIndex).getMarks().set(meetingIndex, certainMarkDto);
			}
		}

		for (CategoryResult result : markTableDto.getTableData())
			Collections.sort(result.getCriteriaResults());

		return markTableDto;
	}

	private int find(List<Criterion> criteria, String name) {
		for (int i = 0; i < criteria.size(); i++)
			if (criteria.get(i).getTitle().equals(name))
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

				int criterionIndex = find(categories.get(category), criterionName);
				int meetingIndex = meetingId.indexOf(currentMeetingId);

				CertainMarkDto certainMarkDto = new CertainMarkDto();
				certainMarkDto.setValue(value);
				certainMarkDto.setCommentary("");
				certainMarkDto.setDescription(description);

				dataTable.get(category).get(criterionIndex).getMarks().set(meetingIndex, certainMarkDto);
			}
		}
	}

	@Override
	public MarkTableDto getMarkTableDto(Long studentId, Long projectId, List<Long> criteriaId,
			List<Long> categoriesId) {

		MarkTableDto markTableDto = getMarkTableDto(studentId, projectId);

		if (categoriesId.isEmpty() && criteriaId.isEmpty())
			return markTableDto;

		List<CategoryResult> categoryResults = markTableDto.getTableData();
		List<CategoryResult> cleanedCategoryResults = new ArrayList<CategoryResult>();

		for (CategoryResult categoryResult : categoryResults) {
			if (categoriesId.contains(categoryResult.getCategoryDto().getId()))
				cleanedCategoryResults.add(categoryResult);
			else {
				List<CriterionResult> tempCriterionResults = new ArrayList<CriterionResult>();

				for (CriterionResult criterionResult : categoryResult.getCriteriaResults())
					if (criteriaId.contains(criterionResult.getCriterionId()))
						tempCriterionResults.add(criterionResult);

				if (!tempCriterionResults.isEmpty()) {
					CategoryResult tempCategoryResult = new CategoryResult();
					tempCategoryResult.setCriteriaResults(tempCriterionResults);
					tempCategoryResult.setCategoryDto(categoryResult.getCategoryDto());
					cleanedCategoryResults.add(tempCategoryResult);
				}
			}

		}
		markTableDto.setTableData(cleanedCategoryResults);
		return markTableDto;
	}

}
