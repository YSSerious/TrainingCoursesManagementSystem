package ua.ukma.nc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.ukma.nc.dto.CriterionHolder;
import ua.ukma.nc.dto.MarkInformation;
import ua.ukma.nc.dto.StudyResultDto;
import ua.ukma.nc.service.ChartService;
import ua.ukma.nc.service.MeetingResultService;

@Service
public class ChartServiceImpl implements ChartService {

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
			
			if(studyList != null){
				studyList.add(studyResultDto);
			}else{
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

}
