package ua.ukma.nc.dao;

import ua.ukma.nc.entity.Mark;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface MarkDao {
    Mark getById(Long id);

    int deleteMark(Mark mark);

    int updateMark(Mark mark);

    List<Mark> getAll();

    int createMark(Mark mark);
}
