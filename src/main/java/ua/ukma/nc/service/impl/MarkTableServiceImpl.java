package ua.ukma.nc.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.ukma.nc.dto.CategoryDto;
import ua.ukma.nc.dto.CategoryResult;
import ua.ukma.nc.dto.CertainMarkDto;
import ua.ukma.nc.dto.CriterionDto;
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
import ua.ukma.nc.entity.Mark;
import ua.ukma.nc.entity.Meeting;
import ua.ukma.nc.entity.MeetingResult;
import ua.ukma.nc.entity.MeetingReview;
import ua.ukma.nc.service.CategoryService;
import ua.ukma.nc.service.FinalReviewCriterionService;
import ua.ukma.nc.service.FinalReviewService;
import ua.ukma.nc.service.MarkService;
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
	private MeetingResultService meetingResultService;
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private MeetingReviewService meetingReviewService;
	
	@Autowired
	private MarkService markService;

	@Override
	public MarkTableDto getMarkTableDto(Long studentId, Long projectId) {
		
		MarkTableDto markTableDto = new MarkTableDto();
		
		List<Mark> marks = markService.getAll();
		List<MarkInformation> marksInformation = meetingResultService.generateMarkInformation(studentId, projectId);
		List<Meeting> meetings = meetingService.getByStudentProject(studentId, projectId);
		List<Criterion> criteria = new ArrayList<Criterion>();
		List<Category> categoriesEntity = categoryService.getByProjectId(projectId);

		List<CategoryDto> categoriesDto = new ArrayList<CategoryDto>();
		
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
					MeetingResultDto meetingResultDto = new MeetingResultDto();
					Mark meetingMark = meetingResult.getMark();
					
					meetingResultDto.setCommentary(meetingResult.getCommentary());
					meetingResultDto.setCriterionName(findCriterionName(criteria, meetingResult.getCriterion().getId()));
					meetingResultDto.setValue(meetingMark.getValue());
					meetingResultDto.setDescription(findDescription(marks, meetingMark.getValue()));
					
					meetingResultsDto.add(meetingResultDto);
				}

				meetingReviewDto.setMarks(meetingResultsDto);
			}

			meetingReviewsDto.add(meetingReviewDto);
		}

		meetingNames.add("Final");
		markTableDto.setMeetings(meetingReviewsDto);

		Map<CategoryDto, List<Criterion>> categories = new TreeMap<CategoryDto, List<Criterion>>();
		
		for(Category categoryEntity: categoriesEntity){
			CategoryDto category = new CategoryDto();
			category.setId(categoryEntity.getId());
			category.setDescription(categoryEntity.getDescription());
			category.setName(categoryEntity.getName());
			
			List<Criterion> criteriaList = categoryEntity.getCriteria();
			categories.put(category, criteriaList);
			criteria.addAll(criteriaList);
			
			categoriesDto.add(category);
		}
		
		List<CriterionDto> criteriaDto = criteria.stream().map(CriterionDto::new)
				.collect(Collectors.toList());
		
		markTableDto.setProjectCriteria(criteriaDto);
		markTableDto.setProjectCategories(categoriesDto);
		
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
				FinalReviewCriterionDto finalReviewCriterionDto = new FinalReviewCriterionDto();
				Mark finalMark = finalReviewCriterion.getMark();
				
				finalReviewCriterionDto.setCommentary(finalReviewCriterion.getCommentary());
				finalReviewCriterionDto.setCriterionName(findCriterionName(criteria, finalReviewCriterion.getCriterion().getId()));
				finalReviewCriterionDto.setValue(finalMark.getValue());
				finalReviewCriterionDto.setDescription(findDescription(marks, finalMark.getValue()));
				
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

			int criterionIndex = find(categories.get(category), markInformation.getCriterionId());
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
				Long criterionId = review.getCriterion().getId();
				Category categoryEntity = review.getCriterion().getCategory();
				Long categoryId = categoryEntity.getId();

				int criterionIndex = find(getCriterionList(categories, categoryId), criterionId);
				int meetingIndex = meetingId.size();

				CertainMarkDto certainMarkDto = new CertainMarkDto();
				certainMarkDto.setValue(String.valueOf(review.getMark().getValue()));
				certainMarkDto.setCommentary(review.getCommentary());
				certainMarkDto.setDescription(findDescription(marks, review.getMark().getValue()));

				getCriterionResultList(dataTable, categoryId).get(criterionIndex).getMarks().set(meetingIndex, certainMarkDto);
			}
		}

		for (CategoryResult result : markTableDto.getTableData())
			Collections.sort(result.getCriteriaResults());

		return markTableDto;
	}
	
	private String findCriterionName(List<Criterion> criteria, Long id) {
		for(Criterion criterion: criteria)
			if(criterion.getId() == id)
				return criterion.getTitle();
		return null;
	}

	private String findDescription(List<Mark> marks, int value) {
		for(Mark mark: marks)
			if(mark.getValue() == value)
				return mark.getDescription();
		return null;
	}

	private List<CriterionResult> getCriterionResultList(Map<CategoryDto, List<CriterionResult>> categories, Long categoryId) {
		for(Entry<CategoryDto, List<CriterionResult>> category: categories.entrySet())
			if(category.getKey().getId() == categoryId)
				return category.getValue();
			
		return null;
	}

	private List<Criterion> getCriterionList(Map<CategoryDto, List<Criterion>> categories, Long categoryId) {
		for(Entry<CategoryDto, List<Criterion>> category: categories.entrySet())
			if(category.getKey().getId() == categoryId)
				return category.getValue();
			
		return null;
	}

	private int find(List<Criterion> criteria, Long id) {
		for (int i = 0; i < criteria.size(); i++)
			if (criteria.get(i).getId() == id)
				return i;

		return -1;
	}

	private void putMarks(List<Meeting> meetings, Map<CategoryDto, List<Criterion>> categories, List<Long> meetingId,
			Map<CategoryDto, List<CriterionResult>> dataTable, String value, String description) {
		for (Meeting meeting : meetings) {
			List<Criterion> currentCriteria = meeting.getCriterions();
			for (Criterion currentCriterion : currentCriteria) {

				Long criterionId = currentCriterion.getId();
				Long currentMeetingId = meeting.getId();
				Category categoryEntity = currentCriterion.getCategory();
				Long categoryId = categoryEntity.getId();

				int criterionIndex = find(getCriterionList(categories, categoryId), criterionId);
				int meetingIndex = meetingId.indexOf(currentMeetingId);

				CertainMarkDto certainMarkDto = new CertainMarkDto();
				certainMarkDto.setValue(value);
				certainMarkDto.setCommentary("");
				certainMarkDto.setDescription(description);

				getCriterionResultList(dataTable, categoryId).get(criterionIndex).getMarks().set(meetingIndex, certainMarkDto);
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
