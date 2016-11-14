package ua.ukma.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ukma.nc.dao.CriterionDao;
import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.service.CriterionService;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Service
public class CriterionServiceImpl implements CriterionService {

	@Autowired
	private CriterionDao criterionDao;

	@Override
	public Criterion getById(Long id) {
		return criterionDao.getById(id);
	}

	@Override
	public Criterion getByName(String name) {
		return criterionDao.getByName(name);
	}

	@Override
	public int deleteCriterion(Criterion criterion) {
		return criterionDao.deleteCriterion(criterion);
	}

	@Override
	public int deleteCriterion(Long id) {
		return criterionDao.deleteCriterion(id);
	}

	@Override
	public int updateCriterion(Criterion criterion) {
		return criterionDao.updateCriterion(criterion);
	}

	@Override
	public List<Criterion> getAll() {
		return criterionDao.getAll();
	}

	@Override
	public int createCriterion(Criterion criterion) {
		return criterionDao.createCriterion(criterion);
	}

	@Override
	public List<Criterion> getByProject(Long projectId) {
		return criterionDao.getByProject(projectId);
	}

	@Override
	public int createCriterion(String title, Long categoryId) {
		return criterionDao.createCriterion(title, categoryId);
	}

	@Override
	public boolean isExistInProjects(Long id) {
		return criterionDao.isExistInProjects(id);
	}

	@Override
	public List<Criterion> getByMeeting(Long meetingId) {
		return criterionDao.getByMeeting(meetingId);
	}
}
