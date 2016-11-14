package ua.ukma.nc.service;

import java.util.List;
import java.util.Map;

import ua.ukma.nc.dto.StudyResultDto;

public interface ChartService {
	Map<String, List<StudyResultDto>> getChartData(Long projectId, Long userId);
	
	Map<String, List<StudyResultDto>> getChartDataFinalReview(Long projectId, Long userId);
}
