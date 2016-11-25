package ua.ukma.nc.service;

import java.util.List;

import ua.ukma.nc.dto.StudentProfile;

public interface StudentService {
	StudentProfile generateStudentProfile(long studentId, long projectId);

	StudentProfile generateStudentProfile(Long student, Long projectId, List<Long> criteria, List<Long> categories);
}
