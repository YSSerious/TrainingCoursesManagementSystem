package ua.ukma.nc.service;

import ua.ukma.nc.entity.FinalReviewCriterion;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface FinalReviewCriterionService {
    FinalReviewCriterion getById(Long id);

    int deleteFinalReviewCriterion(FinalReviewCriterion finalReviewCriterion);

    int updateFinalReviewCriterion(FinalReviewCriterion finalReviewCriterion);

    List<FinalReviewCriterion> getAll();

    int createFinalReviewCriterion(FinalReviewCriterion finalReviewCriterion);
}
