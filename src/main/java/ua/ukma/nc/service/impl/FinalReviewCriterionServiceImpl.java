package ua.ukma.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ukma.nc.dao.FinalReviewCriterionDao;
import ua.ukma.nc.entity.FinalReviewCriterion;
import ua.ukma.nc.service.FinalReviewCriterionService;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Service
public class FinalReviewCriterionServiceImpl implements FinalReviewCriterionService {
    @Autowired
    private FinalReviewCriterionDao finalReviewCriterionDao;

    @Override
    public FinalReviewCriterion getById(Long id) {
        return finalReviewCriterionDao.getById(id);
    }

    @Override
    public int deleteFinalReviewCriterion(FinalReviewCriterion finalReviewCriterion) {
        return finalReviewCriterionDao.deleteFinalReviewCriterion(finalReviewCriterion);
    }

    @Override
    public int updateFinalReviewCriterion(FinalReviewCriterion finalReviewCriterion) {
        return finalReviewCriterionDao.updateFinalReviewCriterion(finalReviewCriterion);
    }

    @Override
    public List<FinalReviewCriterion> getAll() {
        return finalReviewCriterionDao.getAll();
    }

    @Override
    public int createFinalReviewCriterion(FinalReviewCriterion finalReviewCriterion) {
        return finalReviewCriterionDao.createFinalReviewCriterion(finalReviewCriterion);
    }

	@Override
	public List<FinalReviewCriterion> getByFinalReview(Long id) {
		return finalReviewCriterionDao.getByFinalReview(id);
	}
}
