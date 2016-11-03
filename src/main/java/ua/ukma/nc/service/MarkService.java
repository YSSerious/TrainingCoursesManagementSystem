package ua.ukma.nc.service;

import ua.ukma.nc.entity.Mark;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface MarkService {
    Mark getByValue(int value);

    int deleteMark(Mark mark);

    int updateMark(Mark mark);

    List<Mark> getAll();

    int createMark(Mark mark);
}
