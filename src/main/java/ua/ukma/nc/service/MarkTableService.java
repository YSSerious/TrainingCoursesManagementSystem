package ua.ukma.nc.service;

import java.util.List;

import ua.ukma.nc.dto.MarkInformation;
import ua.ukma.nc.dto.MarkTableDto;

public interface MarkTableService {
	MarkTableDto getMarkTableDto(Long studentId, Long projectId,List<MarkInformation> marksInformation );
}
