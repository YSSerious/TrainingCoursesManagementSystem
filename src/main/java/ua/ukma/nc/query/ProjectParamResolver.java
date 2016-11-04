package ua.ukma.nc.query;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;

import java.sql.Date;

import ua.ukma.nc.controller.ProjectsController;
import ua.ukma.nc.query.clause.general.OrClause;
import ua.ukma.nc.query.clause.general.WhereClause;
import ua.ukma.nc.query.clause.project.CriterionLogicClause;
import ua.ukma.nc.query.clause.project.DateLogicClause;
import ua.ukma.nc.query.clause.project.StatusLogicClause;

@Service
public class ProjectParamResolver {

	private String order;
	private Integer page;
	private String[] statuses;
	private Long[] criteria;
	private String text;
	private Date start;
	private Date end;
	private String dateType;

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Integer getPage() {
		if (page == null)
			return 1;
		else
			return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String[] getStatuses() {
		return statuses;
	}

	public void setStatuses(String[] statuses) {
		this.statuses = statuses;
	}

	public Long[] getCriteria() {
		return criteria;
	}

	public void setCriteria(Long[] criteria) {
		this.criteria = criteria;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getLine() {
		String s = "&";

		if (statuses != null)
			for (String status : statuses)
				s += "status=" + status + "&";

		if (criteria != null)
			for (Long criterion : criteria)
				s += "criteria=" + criterion + "&";

		if (text != null)
			s += "text-search=" + text + "&";

		if (order != null)
			s += "order=" + order + "&";

		if (start != null)
			s += "start-date=" + new SimpleDateFormat("yyyy-MM-dd").format(start) + "&";

		if (end != null)
			s += "end-date=" + new SimpleDateFormat("yyyy-MM-dd").format(end) + "&";

		if (dateType != null)
			s += "date-type=" + dateType + "&";

		if (s.equals("&"))
			return "";
		return s.substring(0, s.length() - 1);

	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public QueryBuilder getQueryBuilder() {
		QueryBuilder queryBuilder = new QueryBuilder();
		queryBuilder.setTable("tcms.project");
		queryBuilder.setCurrentPage(getPage());
		queryBuilder.setItemsPerPage(ProjectsController.ITEMS_PER_PAGE);

		if (order != null && isOrderValid(order))
			if (order.contains("asc")) {
				queryBuilder.setOrderBy(order.replace("asc", ""));
				queryBuilder.setOrderType("asc");
			} else if (order.contains("desc")) {
				queryBuilder.setOrderBy(order.replace("desc", ""));
				queryBuilder.setOrderType("desc");
			}

		if (text != null) {

			OrClause orClause = new OrClause();

			WhereClause whereParam1 = new WhereClause();
			whereParam1.setOperation(WhereClause.STRING_CONTAINS);
			whereParam1.setParam(text);
			whereParam1.setColumn("name");

			WhereClause whereParam2 = new WhereClause();
			whereParam2.setOperation(WhereClause.STRING_CONTAINS);
			whereParam2.setParam(text);
			whereParam2.setColumn("description");

			orClause.addValue(whereParam1.generateClause());
			orClause.addValue(whereParam2.generateClause());

			queryBuilder.putClause(orClause);

			if (statuses != null) {
				orClause = new OrClause();
				for (String status : statuses) {

					StatusLogicClause statusLogicClause = new StatusLogicClause();
					statusLogicClause.setStatus(status);
					orClause.addValue(statusLogicClause.generateClause());
				}

				queryBuilder.putClause(orClause);
			}

			if (criteria != null)
				for (Long criterion : criteria) {
					CriterionLogicClause criterionLogicClause = new CriterionLogicClause();
					criterionLogicClause.setCriterionId(criterion);
					queryBuilder.putClause(criterionLogicClause);
				}

			if (dateType != null && !(start == null || end == null)) {
				DateLogicClause dateLogicClause = new DateLogicClause();
				dateLogicClause.setFinish(end);
				dateLogicClause.setStart(start);
				dateLogicClause.setType(dateType);
				queryBuilder.putClause(dateLogicClause);
			}
		}
		return queryBuilder;
	}

	private boolean isOrderValid(String order) {
		return order.equals(ProjectsController.END_ASC) || order.equals(ProjectsController.NAME_ASC)
				|| order.equals(ProjectsController.START_ASC) || order.equals(ProjectsController.END_DESC)
				|| order.equals(ProjectsController.NAME_DESC) || order.equals(ProjectsController.START_DESC);
	}
}
