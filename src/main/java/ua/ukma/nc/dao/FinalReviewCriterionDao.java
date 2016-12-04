package ua.ukma.nc.dao;

import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.FinalReviewCriterion;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface FinalReviewCriterionDao {
    FinalReviewCriterion getById(Long id);
    
    List<FinalReviewCriterion> getByFinalReview(Long id);

    int deleteFinalReviewCriterion(FinalReviewCriterion finalReviewCriterion);

    int updateFinalReviewCriterion(FinalReviewCriterion finalReviewCriterion);

    List<FinalReviewCriterion> getAll();

    int createFinalReviewCriterion(FinalReviewCriterion finalReviewCriterion);

    boolean isExists(Criterion criterion, Long projectId);
}
