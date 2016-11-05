package ua.ukma.nc.query.clause.project;

import java.util.Date;

import ua.ukma.nc.query.ProjectSearch;
import ua.ukma.nc.query.clause.general.Clause;
import ua.ukma.nc.query.clause.general.WhereClause;

public class DateLogicClause implements Clause {

	public DateLogicClause(Date start, Date finish, String type) {
		this.start = start;
		this.finish = finish;
		this.type = type;
	}

	private Date start;
	private Date finish;

	private String type;

	@Override
	public String generateClause() {

		String query = null;

		if (type == null || (start == null && finish == null))
			throw new NullPointerException("type cannot be null");

		if (type.equals(ProjectSearch.START)) {

			WhereClause whereParam = new WhereClause();

			if (start != null) {
				whereParam.setColumn("start");
				whereParam.setOperation(WhereClause.MORE);
				whereParam.setParam(start);
				query = "";
				query += whereParam.generateClause();
			}

			if (finish != null) {
				whereParam.setColumn("start");
				whereParam.setOperation(WhereClause.LESS);
				whereParam.setParam(finish);

				if (query == null)
					query = "";
				else
					query += " AND ";

				query += whereParam.generateClause();
			}

		} else if (type.equals(ProjectSearch.END)) {

			WhereClause whereParam = new WhereClause();

			if (start != null) {
				whereParam.setColumn("finish");
				whereParam.setOperation(WhereClause.MORE);
				whereParam.setParam(start);
				query = "";
				query += whereParam.generateClause();
			}

			if (finish != null) {
				whereParam.setColumn("finish");
				whereParam.setOperation(WhereClause.LESS);
				whereParam.setParam(finish);

				if (query == null)
					query = "";
				else
					query += " AND ";

				query += whereParam.generateClause();
			}

		} else {
			WhereClause whereParam = new WhereClause();

			if (start != null) {
				whereParam.setColumn("start");
				whereParam.setOperation(WhereClause.MORE);
				whereParam.setParam(start);
				query = "";
				query += whereParam.generateClause();
			}

			if (finish != null) {
				whereParam.setColumn("finish");
				whereParam.setOperation(WhereClause.LESS);
				whereParam.setParam(finish);

				if (query == null)
					query = "";
				else
					query += " AND ";

				query += whereParam.generateClause();
			}

		}

		return query;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getFinish() {
		return finish;
	}

	public void setFinish(Date finish) {
		this.finish = finish;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
