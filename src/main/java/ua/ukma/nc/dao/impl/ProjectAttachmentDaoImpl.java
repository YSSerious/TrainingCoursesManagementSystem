package ua.ukma.nc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.ukma.nc.dao.ProjectAttachmentDao;
import ua.ukma.nc.entity.ProjectAttachment;
import ua.ukma.nc.entity.impl.proxy.ProjectProxy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Repository
public class ProjectAttachmentDaoImpl implements ProjectAttachmentDao{

    private static Logger log = LoggerFactory.getLogger(ProjectAttachmentDaoImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ApplicationContext appContext;

    public class ProjectAttachmentMapper implements RowMapper<ProjectAttachment> {
        public ProjectAttachment mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            ProjectAttachment projectAttachment = new ProjectAttachment();
            projectAttachment.setId(resultSet.getLong("id"));
            projectAttachment.setName(resultSet.getString("name"));
            projectAttachment.setProject(appContext.getBean(ProjectProxy.class, resultSet.getLong("id_project")));
            projectAttachment.setAttachmentScope(resultSet.getString("attachment_scope"));
            return projectAttachment;
        }
    }

    private static final String GET_ALL = "SELECT id, name, id_project, attachment_scope FROM tcms.project_attachment";

    private static final String GET_BY_ID = "SELECT id, name, id_project, attachment_scope FROM tcms.project_attachment WHERE id = ?";

    private static final String DELETE_PROJECT_ATTACHMENT = "DELETE FROM tcms.project_attachment WHERE id = ?";

    private static final String CREATE_PROJECT_ATTACHMENT = "INSERT INTO tcms.project_attachment (name, id_project, attachment_scope) VALUES (?,?,?)";

    private static final String UPDATE_PROJECT_ATTACHMENT = "UPDATE tcms.project_attachment SET name = ?, id_project = ?, attachment_scope = ? WHERE id = ?";

    @Override
    public ProjectAttachment getById(Long id) {
        log.info("Getting project attachment with id = {}", id);
        return jdbcTemplate.queryForObject(GET_BY_ID, new ProjectAttachmentMapper(), id);
    }

    @Override
    public int deleteProjectAttachment(ProjectAttachment projectAttachment) {
        log.info("Deleting project attachment with id = {}", projectAttachment.getId());
        return jdbcTemplate.update(DELETE_PROJECT_ATTACHMENT, projectAttachment.getId());
    }

    @Override
    public int updateProjectAttachment(ProjectAttachment projectAttachment) {
        log.info("Updating project attachment with id = {}", projectAttachment.getId());
        return jdbcTemplate.update(UPDATE_PROJECT_ATTACHMENT, projectAttachment.getName(), projectAttachment.getProject().getId(), projectAttachment.getAttachmentScope(), projectAttachment.getId());
    }

    @Override
    public List<ProjectAttachment> getAll() {
        log.info("Getting all project attachments");
        return jdbcTemplate.query(GET_ALL, new ProjectAttachmentMapper());
    }

    @Override
    public int createProjectAttachment(ProjectAttachment projectAttachment) {
        log.info("Create new project attachment with name = {}", projectAttachment.getName());
        return jdbcTemplate.update(CREATE_PROJECT_ATTACHMENT, projectAttachment.getName(), projectAttachment.getProject().getId(), projectAttachment.getAttachmentScope());
    }
}
