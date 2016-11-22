package ua.ukma.nc.service;

import java.util.List;

import ua.ukma.nc.dto.MarkTableDto;

public interface MarkTableService {
	MarkTableDto getMarkTableDto(Long studentId, Long projectId);
	MarkTableDto getMarkTableDto(Long studentId, Long projectId, List<Long> criteriaId, List<Long> categoriesId);
}
