package ua.ukma.nc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.ukma.nc.dto.CriterionHolder;
import ua.ukma.nc.dto.MarkInformation;
import ua.ukma.nc.dto.StudyResultDto;
import ua.ukma.nc.entity.FinalReview;
import ua.ukma.nc.entity.FinalReviewCriterion;
import ua.ukma.nc.service.ChartService;
import ua.ukma.nc.service.FinalReviewCriterionService;
import ua.ukma.nc.service.FinalReviewService;
import ua.ukma.nc.service.MeetingResultService;

@Service
public class ChartServiceImpl implements ChartService {

	@Autowired
	private FinalReviewService finalReviewService;

	@Autowired
	private FinalReviewCriterionService finalReviewCriterionService;

	@Autowired
	private MeetingResultService meetingResultService;

	@Override
	public Map<String, List<StudyResultDto>> getChartData(Long projectId, Long studentId) {
		List<MarkInformation> marksInformation = meetingResultService.generateMarkInformation(studentId, projectId);

		Map<String, CriterionHolder> criterions = new HashMap<String, CriterionHolder>();
		for (MarkInformation markInformation : marksInformation) {
			CriterionHolder criterionHolder = criterions.get(markInformation.getCriterionName());

			if (criterionHolder != null)
				criterionHolder.getMarks().add(markInformation.getMark());
			else {
				criterionHolder = new CriterionHolder();
				List<Integer> currentMarks = new ArrayList<Integer>();
				currentMarks.add(markInformation.getMark());

				criterionHolder.setMarks(currentMarks);
				criterionHolder.setCategory(markInformation.getCategory());

				criterions.put(markInformation.getCriterionName(), criterionHolder);
			}
		}
		Map<String, List<StudyResultDto>> result = new HashMap<String, List<StudyResultDto>>();

		for (String key : criterions.keySet()) {
			CriterionHolder criterionHolder = criterions.get(key);
			String category = criterionHolder.getCategory();

			float average = calculateAverage(criterionHolder.getMarks());

			StudyResultDto studyResultDto = new StudyResultDto();
			studyResultDto.setAverageValue(average);
			studyResultDto.setCriterionName(key);

			List<StudyResultDto> studyList = result.get(category);

			if (studyList != null) {
				studyList.add(studyResultDto);
			} else {
				List<StudyResultDto> currentList = new ArrayList<StudyResultDto>();
				currentList.add(studyResultDto);

				result.put(category, currentList);
			}

		}

		return result;
	}

	private float calculateAverage(List<Integer> marks) {
		float total = 0;
		float quantity = 0;

		for (Integer mark : marks) {
			++quantity;
			total += mark;
		}

		return total / quantity;
	}

	@Override
	public Map<String, List<StudyResultDto>> getChartDataFinalReview(Long projectId, Long userId) {

		Map<String, List<StudyResultDto>> result = new TreeMap<String, List<StudyResultDto>>();

		try {
			FinalReview review = finalReviewService.getByStudent(projectId, userId, "M");

			List<FinalReviewCriterion> marks = finalReviewCriterionService.getByFinalReview(review.getId());

			for (FinalReviewCriterion reviewCriterion : marks) {
				String category = reviewCriterion.getCriterion().getCategory().getName();

				List<StudyResultDto> currentList = result.get(category);

				StudyResultDto studyResultDto = new StudyResultDto();
				studyResultDto.setCriterionName(reviewCriterion.getCriterion().getTitle());
				studyResultDto.setAverageValue(reviewCriterion.getMark().getValue());
				if (currentList == null) {
					List<StudyResultDto> newList = new ArrayList<StudyResultDto>();
					newList.add(studyResultDto);
					result.put(category, newList);
				} else {
					currentList.add(studyResultDto);
				}
			}
		} catch (Exception e) {
			return result;
		}
		return result;
	}

}
