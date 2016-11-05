package ua.ukma.nc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.ukma.nc.dao.GroupAttachmentDao;
import ua.ukma.nc.entity.GroupAttachment;
import ua.ukma.nc.entity.impl.proxy.GroupProxy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Repository
public class GroupAttachmentDaoImpl implements GroupAttachmentDao{

    private static Logger log = LoggerFactory.getLogger(GroupAttachmentDaoImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ApplicationContext context;

    public class GroupAttachmentMapper implements RowMapper<GroupAttachment> {
        public GroupAttachment mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            GroupAttachment groupAttachment = new GroupAttachment();
            groupAttachment.setId(resultSet.getLong("id"));
            groupAttachment.setName(resultSet.getString("name"));
            groupAttachment.setGroup(context.getBean(GroupProxy.class,resultSet.getLong("id_group")));
            groupAttachment.setAttachmentScope(resultSet.getString("attachment_scope"));
            return groupAttachment;
        }
    }

    private static final String GET_ALL = "SELECT id, name, id_group, attachment_scope FROM tcms.group_attachment";

    private static final String GET_BY_ID = "SELECT id, name, id_group, attachment_scope FROM tcms.group_attachment WHERE id = ?";

    private static final String DELETE_GROUP_ATTACHMENT = "DELETE FROM tcms.group_attachment WHERE id = ?";

    private static final String CREATE_GROUP_ATTACHMENT = "INSERT INTO tcms.group_attachment (name, id_group, attachment_scope) VALUES (?,?,?)";

    private static final String UPDATE_GROUP_ATTACHMENT = "UPDATE tcms.group_attachment SET name = ?, id_group = ?, attachment_scope = ? WHERE id = ?";

    @Override
    public GroupAttachment getById(Long id) {
        log.info("Getting group attachment with id = {}", id);
        return jdbcTemplate.queryForObject(GET_BY_ID, new GroupAttachmentMapper(), id);
    }

    @Override
    public int deleteGroupAttachment(GroupAttachment groupAttachment) {
        log.info("Deleting group attachment with id = {}", groupAttachment.getId());
        return jdbcTemplate.update(DELETE_GROUP_ATTACHMENT, groupAttachment.getId());
    }

    @Override
    public int updateGroupAttachment(GroupAttachment groupAttachment) {
        log.info("Updating group attachment with id = {}", groupAttachment.getId());
        return jdbcTemplate.update(UPDATE_GROUP_ATTACHMENT, groupAttachment.getName(), groupAttachment.getGroup().getId(), groupAttachment.getAttachmentScope(), groupAttachment.getId());
    }

    @Override
    public List<GroupAttachment> getAll() {
        log.info("Getting all group attachments");
        return jdbcTemplate.query(GET_ALL, new GroupAttachmentMapper());
    }

    @Override
    public int createGroupAttachment(GroupAttachment groupAttachment) {
        log.info("Create new group attachment with name = {}", groupAttachment.getName());
        return jdbcTemplate.update(CREATE_GROUP_ATTACHMENT,groupAttachment.getName(), groupAttachment.getGroup().getId(), groupAttachment.getAttachmentScope());
    }
}
