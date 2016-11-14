package ua.ukma.nc.service;

import ua.ukma.nc.entity.Criterion;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface CriterionService {
    Criterion getById(Long id);

    Criterion getByName(String name);

    int deleteCriterion(Criterion criterion);

    int deleteCriterion(Long id);

    int updateCriterion(Criterion criterion);

    List<Criterion> getAll();
    
    List<Criterion> getByProject(Long projectId);

    List<Criterion> getProjectUnusedCriteria (Long projectId);

    int createCriterion(Criterion criterion);

    int createCriterion(String title, Long categoryId);

    boolean isExistInProjects(Long id);

	List<Criterion> getByMeeting(Long meetingId);
}
