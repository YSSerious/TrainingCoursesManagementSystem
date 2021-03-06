package ua.ukma.nc.service;

import ua.ukma.nc.entity.Criterion;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface CriterionService {
	
	List<Criterion> getByProjects(List<Long> projects);
	
    Criterion getById(Long id);

    Criterion getByName(String name);

    List<Criterion> getByNames(List<String> names);

    int deleteCriterion(Criterion criterion);

    int deleteCriterion(Long id);

    int updateCriterion(Criterion criterion);

    List<Criterion> getAll();
    
    List<Criterion> getByProject(Long projectId);

    List<Criterion> getByMeeting(Long meetingId);

    List<Criterion> getProjectUnusedCriteria (Long projectId);

    List<Criterion> getMeetingUnusedCriteria (Long meetingId, Long projectId);

    int createCriterion(Criterion criterion);

    int createCriterion(String title, Long categoryId);

    boolean isExistInProjects(Long id);

    boolean isRatedInProject(Long projectId, Criterion criterion);

    boolean isRatedInMeeting(Long meetingId, Criterion criterion);

    boolean isExist(String title);
}
