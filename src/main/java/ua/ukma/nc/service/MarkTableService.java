package ua.ukma.nc.service;

import ua.ukma.nc.dto.MarkTableDto;

public interface MarkTableService {
	MarkTableDto getMarkTableDto(Long studentId, Long projectId);
}
