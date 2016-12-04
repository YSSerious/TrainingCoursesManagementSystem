package ua.ukma.nc.query;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import ua.ukma.nc.query.clause.general.OrClause;
import ua.ukma.nc.query.clause.general.WhereClause;
import ua.ukma.nc.query.clause.project.CriterionLogicClause;
import ua.ukma.nc.query.clause.project.DateLogicClause;
import ua.ukma.nc.query.clause.project.MentorLogicClause;
import ua.ukma.nc.query.clause.project.StatusLogicClause;

@Service
public class ProjectParamResolver {

	public static final int ITEMS_PER_PAGE = 7;

	public QueryBuilder getQueryBuilder(ProjectSearch projectSearch) {

		Integer page = projectSearch.getPage();
		String order = projectSearch.getOrder();
		String text = projectSearch.getText();
		String[] statuses = projectSearch.getStatuses();
		Long[] criteria = projectSearch.getCriteria();
		Date start = projectSearch.getStart();
		Date end = projectSearch.getEnd();
		String dateType = projectSearch.getDateType();

		QueryBuilder queryBuilder = new QueryBuilder();
		queryBuilder.setTable("tcms.project");
		queryBuilder.setCurrentPage(page);
		queryBuilder.setItemsPerPage(ITEMS_PER_PAGE);

		if (order != null) {
			if (order.contains("asc")) {
				queryBuilder.setOrderBy(order.replace("asc", ""));
				queryBuilder.setOrderType("asc");
			} else if (order.contains("desc")) {
				queryBuilder.setOrderBy(order.replace("desc", ""));
				queryBuilder.setOrderType("desc");
			}
		} else {
			queryBuilder.setOrderBy("start");
			queryBuilder.setOrderType("desc");
		}

		if (text != null) {
			String[] words = text.split("[^\\p{L}]+");

			for (String word : words) {
				OrClause orClause = new OrClause();

				WhereClause whereParam1 = new WhereClause();
				whereParam1.setOperation(WhereClause.STRING_CONTAINS);
				whereParam1.setParam(word);
				whereParam1.setColumn("name");

				WhereClause whereParam2 = new WhereClause();
				whereParam2.setOperation(WhereClause.STRING_CONTAINS);
				whereParam2.setParam(word);
				whereParam2.setColumn("description");

				orClause.addValue(whereParam1);
				orClause.addValue(whereParam2);

				queryBuilder.putClause(orClause);
			}
		}

		if (statuses != null) {
			OrClause orClause = new OrClause();
			for (String status : statuses) {

				StatusLogicClause statusLogicClause = new StatusLogicClause(status);
				orClause.addValue(statusLogicClause);
			}

			queryBuilder.putClause(orClause);
		}

		if (criteria != null)
			for (Long criterion : criteria) {
				CriterionLogicClause criterionLogicClause = new CriterionLogicClause(criterion);
				queryBuilder.putClause(criterionLogicClause);
			}
		
		if(dateType == null)
			dateType = ProjectSearch.START;

		if (start != null || end != null) {
			DateLogicClause dateLogicClause = new DateLogicClause(start, end, dateType);

			queryBuilder.putClause(dateLogicClause);
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		boolean showAllProjects = authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
				|| authorities.contains(new SimpleGrantedAuthority("ROLE_HR"));

		if (!showAllProjects) {
			String name = authentication.getName();
			queryBuilder.putClause(new MentorLogicClause(name));
		}
		return queryBuilder;
	}

}
