package ua.ukma.nc.service;

import ua.ukma.nc.dto.StudentProfile;

public interface StudentService {
	StudentProfile generateStudentProfile(long studentId, long projectId);
}
