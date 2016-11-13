package ua.ukma.nc.service;

import ua.ukma.nc.entity.FinalReview;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface FinalReviewService {
	
	FinalReview getByStudent(Long projectId, Long studentId, String type);
	
	boolean exists(Long studentId, Long groupId, String type);
	
    FinalReview getById(Long id);

    int deleteFinalReview(FinalReview finalReview);

    int updateFinalReview(FinalReview finalReview);

    List<FinalReview> getAll();

    int createFinalReview(FinalReview finalReview);
}
