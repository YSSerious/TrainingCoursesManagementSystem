package ua.ukma.nc.dao;

import ua.ukma.nc.entity.FinalReview;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface FinalReviewDao {
	boolean exists(Long studentId, Long groupId, String type);
	
    FinalReview getById(Long id);
    
    FinalReview getByStudent(Long projectId, Long studentId, String type);

    int deleteFinalReview(FinalReview finalReview);

    int updateFinalReview(FinalReview finalReview);

    List<FinalReview> getAll();

    int createFinalReview(FinalReview finalReview);
}
