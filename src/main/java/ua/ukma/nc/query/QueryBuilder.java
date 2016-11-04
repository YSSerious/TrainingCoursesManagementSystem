package ua.ukma.nc.query;

import java.util.ArrayList;
import java.util.List;

import ua.ukma.nc.query.clause.general.Clause;

public class QueryBuilder {

	private List<Clause> clauses;

	private Integer itemsPerPage;
	private Integer currentPage;

	private String orderBy;
	private String orderType;

	public static final String ASC = "ASC";
	public static final String DESC = "DESC";

	private String table;

	public QueryBuilder() {
		clauses = new ArrayList<Clause>();
	}

	public int getItemsPerPage() {
		return itemsPerPage;
	}

	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public void putClause(Clause param) {
		clauses.add(param);
	}

	public String generateQuery() {
		if (table == null)
			throw new NullPointerException("Table name can't be null");

		String result = "SELECT * FROM " + table + "\n";

		if (!clauses.isEmpty())
			result += "WHERE ";

		for (Clause clause : clauses)
			result += clause.generateClause() + " AND ";

		if (!clauses.isEmpty())
			result = result.substring(0, result.length() - 4) + "\n";

		if (orderBy != null && orderType != null)
			result += "ORDER BY " + orderBy + " " + orderType + "\n";

		if (currentPage != null && itemsPerPage != null)
			result += "LIMIT " + itemsPerPage + " OFFSET " + (currentPage - 1) * itemsPerPage;

		return result;
	}

	public String generateCountQuery() {
		if (table == null)
			throw new NullPointerException("Table name can't be null");

		String result = "SELECT COUNT(*) AS QUANTITY FROM " + table + "\n";

		if (!clauses.isEmpty())
			result += "WHERE ";

		for (Clause clause : clauses)
			result += clause.generateClause() + " AND ";

		if (!clauses.isEmpty())
			result = result.substring(0, result.length() - 4) + "\n";

		return result;
	}
}
