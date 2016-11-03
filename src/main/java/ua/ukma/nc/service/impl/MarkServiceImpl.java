package ua.ukma.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ukma.nc.dao.MarkDao;
import ua.ukma.nc.entity.Mark;
import ua.ukma.nc.service.MarkService;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Service
public class MarkServiceImpl implements MarkService{

    @Autowired
    private MarkDao markDao;

    @Override
    public Mark getByValue(int value) {
        return markDao.getByValue(value);
    }

    @Override
    public int deleteMark(Mark mark) {
        return markDao.deleteMark(mark);
    }

    @Override
    public int updateMark(Mark mark) {
        return markDao.updateMark(mark);
    }

    @Override
    public List<Mark> getAll() {
        return markDao.getAll();
    }

    @Override
    public int createMark(Mark mark) {
        return markDao.createMark(mark);
    }
}
