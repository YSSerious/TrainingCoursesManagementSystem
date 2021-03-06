package ua.ukma.nc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.ukma.nc.dao.ProjectAttachmentDao;
import ua.ukma.nc.entity.ProjectAttachment;
import ua.ukma.nc.entity.impl.proxy.ProjectProxy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            projectAttachment.setAttachment(resultSet.getBytes("attachment"));
            return projectAttachment;
        }
    }

    private static final String GET_ALL = "SELECT id, name, id_project, attachment_scope, attachment FROM tcms.project_attachment";

    private static final String GET_BY_ID = "SELECT id, name, id_project, attachment_scope, attachment FROM tcms.project_attachment WHERE id = ?";
    
    private static final String GET_ALL_BY_ID = "SELECT id, name, id_project, attachment_scope, attachment FROM tcms.project_attachment WHERE id_project = ?";

    private static final String DELETE_PROJECT_ATTACHMENT = "DELETE FROM tcms.project_attachment WHERE id = ?";

    private static final String CREATE_PROJECT_ATTACHMENT = "INSERT INTO tcms.project_attachment (name, id_project, attachment_scope, attachment) VALUES (?,?,?,?)";

    private static final String UPDATE_PROJECT_ATTACHMENT = "UPDATE tcms.project_attachment SET name = ?, id_project = ?, attachment_scope = ?, attachment = ? WHERE id = ?";

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
        return jdbcTemplate.update(UPDATE_PROJECT_ATTACHMENT, projectAttachment.getName(), projectAttachment.getProject().getId(), projectAttachment.getAttachmentScope(), projectAttachment.getAttachment(), projectAttachment.getId());
    }

    @Override
    public List<ProjectAttachment> getAll() {
        log.info("Getting all project attachments");
        return jdbcTemplate.query(GET_ALL, new ProjectAttachmentMapper());
    }

    @Override
    public Long createProjectAttachment(ProjectAttachment projectAttachment) {
    	KeyHolder holder = new GeneratedKeyHolder();

    	jdbcTemplate.update(new PreparedStatementCreator() {           

    	                @Override
    	                public PreparedStatement createPreparedStatement(Connection connection)
    	                        throws SQLException {
    	                    PreparedStatement ps = connection.prepareStatement(CREATE_PROJECT_ATTACHMENT, Statement.RETURN_GENERATED_KEYS);
    	                    ps.setString(1, projectAttachment.getName());
    	                    ps.setLong(2, projectAttachment.getProject().getId());
    	                    ps.setString(3, projectAttachment.getAttachmentScope());
    	                    ps.setBytes(4, projectAttachment.getAttachment());
    	                    return ps;
    	                }
    	            }, holder);

    	return (Long) holder.getKeys().get("id");
    }
    
    @Override
    public List<ProjectAttachment> getAllById(Long id_project) {
        log.info("Getting certain project attachments");
        return jdbcTemplate.query(GET_ALL_BY_ID, new ProjectAttachmentMapper(), id_project);
    }
    
    
}
