package ua.ukma.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ukma.nc.dao.FinalReviewDao;
import ua.ukma.nc.entity.FinalReview;
import ua.ukma.nc.service.FinalReviewService;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Service
public class FinalReviewServiceImpl implements FinalReviewService{

    @Autowired
    private FinalReviewDao finalReviewDao;

    @Override
    public FinalReview getById(Long id) {
        return finalReviewDao.getById(id);
    }

    @Override
    public int deleteFinalReview(FinalReview finalReview) {
        return finalReviewDao.deleteFinalReview(finalReview);
    }

    @Override
    public int updateFinalReview(FinalReview finalReview) {
        return finalReviewDao.updateFinalReview(finalReview);
    }

    @Override
    public List<FinalReview> getAll() {
        return finalReviewDao.getAll();
    }

    @Override
    public int createFinalReview(FinalReview finalReview) {
        return finalReviewDao.createFinalReview(finalReview);
    }

	@Override
	public boolean exists(Long studentId, Long groupId, String type) {
		return finalReviewDao.exists(studentId, groupId, type);
	}

	@Override
	public FinalReview getByStudent(Long projectId, Long studentId, String type) {
		return finalReviewDao.getByStudent(projectId, studentId, type);
	}

	@Override
	public boolean existsForProject(Long studentId, Long projectId, String type) {
		return finalReviewDao.existsForProject(studentId, projectId, type);
	}
}
