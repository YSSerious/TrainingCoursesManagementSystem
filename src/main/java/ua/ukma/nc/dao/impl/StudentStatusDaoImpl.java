package ua.ukma.nc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.ukma.nc.dao.StudentStatusDao;
import ua.ukma.nc.entity.StudentStatus;
import ua.ukma.nc.entity.impl.proxy.StatusProxy;
import ua.ukma.nc.entity.impl.proxy.UserProxy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Repository
public class StudentStatusDaoImpl implements StudentStatusDao{

    private static Logger log = LoggerFactory.getLogger(StudentStatusDaoImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public class StudentStatusMapper implements RowMapper<StudentStatus> {
        public StudentStatus mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            StudentStatus studentStatus = new StudentStatus();
            studentStatus.setStudent(new UserProxy(resultSet.getLong("id_student")));
            studentStatus.setStatus(new StatusProxy(resultSet.getLong("id_status")));
            return studentStatus;
        }
    }

    private static final String GET_ALL = "SELECT id_student, id_status FROM tcms.student_status";

    private static final String GET_BY_STUDENT_ID = "SELECT id_student, id_status FROM tcms.student_status WHERE id_student = ?";

    private static final String DELETE_STUDENT_STATUS = "DELETE FROM tcms.student_status WHERE id_student = ?";

    private static final String CREATE_STUDENT_STATUS = "INSERT INTO tcms.student_status (id_student, id_status) VALUES (?,?)";

    private static final String UPDATE_STUDENT_STATUS = "UPDATE tcms.student_status SET id_status = ? WHERE id_student = ?";

    @Override
    public StudentStatus getByUserId(Long id) {
        log.info("Getting student status with student id = {}", id);
        return jdbcTemplate.queryForObject(GET_BY_STUDENT_ID, new StudentStatusMapper(), id);
    }

    @Override
    public int deleteStudentStatus(StudentStatus studentStatus) {
        log.info("Deleting student status with student id = {}", studentStatus.getStudent().getId());
        return jdbcTemplate.update(DELETE_STUDENT_STATUS, studentStatus.getStudent().getId());
    }

    @Override
    public int updateStudentStatus(StudentStatus studentStatus) {
        log.info("Updating student status with student id = {}", studentStatus.getStudent().getId());
        return jdbcTemplate.update(UPDATE_STUDENT_STATUS, studentStatus.getStatus().getId(), studentStatus.getStudent().getId());
    }

    @Override
    public List<StudentStatus> getAll() {
        log.info("Getting all students statuses");
        return jdbcTemplate.query(GET_ALL, new StudentStatusMapper());
    }

    @Override
    public int createStudentStatus(StudentStatus studentStatus) {
        log.info("Create new student status for student with id = {}", studentStatus.getStudent().getId());
        return jdbcTemplate.update(CREATE_STUDENT_STATUS, studentStatus.getStudent().getId(), studentStatus.getStatus().getId());
    }
}
