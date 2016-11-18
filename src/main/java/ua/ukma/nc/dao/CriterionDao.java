package ua.ukma.nc.dao;

import ua.ukma.nc.entity.Criterion;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface CriterionDao {
    Criterion getById(Long id);

    Criterion getByName(String name);

    List<Criterion> getProjectUnusedCriteria (Long projectId);

    int deleteCriterion(Criterion criterion);

    int deleteCriterion(Long id);

    int deleteByCategoryId(Long id);

    int updateCriterion(Criterion criterion);

    List<Criterion> getAll();
    
    List<Criterion> getByProject(Long projectId);

    int createCriterion(Criterion criterion);

    int createCriterion(String title, Long categoryId);

    boolean isExistInProjects(Long id);
    
	List<Criterion> getByMeeting(Long meetingId);

    boolean isRatedInProject(Long projectId, Criterion criterion);
}
