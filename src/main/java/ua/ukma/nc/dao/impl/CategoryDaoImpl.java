package ua.ukma.nc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.ukma.nc.dao.CategoryDao;
import ua.ukma.nc.entity.Category;
import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.impl.proxy.CriterionProxy;
import ua.ukma.nc.entity.impl.real.CategoryImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
@Repository
public class CategoryDaoImpl implements CategoryDao {
	private static Logger log = LoggerFactory.getLogger(CategoryDaoImpl.class.getName());

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ApplicationContext appContext;

	public class CategoryMapper implements RowMapper<Category> {
		public Category mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			Category category = new CategoryImpl();
			category.setId(resultSet.getLong("id"));
			category.setName(resultSet.getString("name"));
			category.setDescription(resultSet.getString("description"));
			category.setCriteria(getCriteria(resultSet.getLong("id")));
			return category;
		}
	}

	private static final String GET_CRITERIA_BY_ID = "SELECT id, name FROM tcms.criterion WHERE id_category = ?";

	private static final String GET_ALL = "SELECT id, name, description FROM tcms.category";

	private static final String GET_BY_ID = "SELECT id, name, description FROM tcms.category WHERE id = ?";

	private static final String DELETE_CATEGORY = "DELETE FROM tcms.category WHERE id = ?";

	private static final String CREATE_CATEGORY = "INSERT INTO tcms.category (name, description) VALUES (?,?)";

	private static final String UPDATE_CATEGORY = "UPDATE tcms.category SET name = ?, description = ? WHERE id = ?";

	@Override
	public Category getById(Long id) {
		log.info("Getting category with id = {}", id);
		return jdbcTemplate.queryForObject(GET_BY_ID, new CategoryMapper(), id);
	}

	@Override
	public int deleteCategory(Category category) {
		log.info("Deleting category with id = {}", category.getId());
		return jdbcTemplate.update(DELETE_CATEGORY, category.getId());
	}

	@Override
	public int updateCategory(Category category) {
		log.info("Updating role with id = {}", category.getId());
		return jdbcTemplate.update(UPDATE_CATEGORY, category.getName(), category.getDescription(), category.getId());
	}

	@Override
	public List<Category> getAll() {
		log.info("Getting all categories");
		return jdbcTemplate.query(GET_ALL, new CategoryMapper());
	}

	@Override
	public int createCategory(Category category) {
		log.info("Create new category with name = {}", category.getName());
		return jdbcTemplate.update(CREATE_CATEGORY, category.getName(), category.getDescription());
	}

	@Override
	public boolean isExist(Category category) {
		return false;
	}

	private List<Criterion> getCriteria(Long categoryId) {
		log.info("Getting all criteria with category id = {}", categoryId);
		return jdbcTemplate.query(GET_CRITERIA_BY_ID, new CategoryCriterionMapper(), categoryId);
	}
	
	public class CategoryCriterionMapper implements RowMapper<Criterion> {
		public Criterion mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			Criterion criterion = appContext.getBean(CriterionProxy.class, resultSet.getLong("id"));
            return criterion;
		}
	}
}
