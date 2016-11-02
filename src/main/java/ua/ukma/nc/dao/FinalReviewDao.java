package ua.ukma.nc.dao;

import ua.ukma.nc.entity.FinalReview;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface FinalReviewDao {
    FinalReview getById(Long id);

    int deleteFinalReview(FinalReview finalReview);

    int updateFinalReview(FinalReview finalReview);

    List<FinalReview> getAll();

    int createFinalReview(FinalReview finalReview);
}
