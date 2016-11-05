package ua.ukma.nc.query.clause.project;

import java.sql.Date;

import ua.ukma.nc.query.ProjectSearch;
import ua.ukma.nc.query.clause.general.Clause;
import ua.ukma.nc.query.clause.general.WhereClause;

public class StatusLogicClause implements Clause {
	private String status;
	
	public StatusLogicClause(String status){
		this.status = status;
	}

	@Override
	public String generateClause() {
		if (status == null)
			throw new NullPointerException("status cannot be null");

		String query = "";
		WhereClause whereParam1 = new WhereClause();
		WhereClause whereParam2 = new WhereClause();

		Date currentDate = new Date(System.currentTimeMillis());
		if (status.equals(ProjectSearch.ACTIVE)) {
			whereParam1.setColumn("start");
			whereParam1.setOperation(WhereClause.LESS);
			whereParam1.setParam(currentDate);

			whereParam2.setColumn("finish");
			whereParam2.setOperation(WhereClause.MORE);
			whereParam2.setParam(currentDate);

			query = "(" + whereParam1.generateClause() + " AND " + whereParam2.generateClause() + ")";
		} else if (status.equals(ProjectSearch.COMPLETED)) {
			whereParam2.setColumn("finish");
			whereParam2.setOperation(WhereClause.LESS);
			whereParam2.setParam(currentDate);

			query = whereParam2.generateClause();

		} else {
			whereParam2.setColumn("start");
			whereParam2.setOperation(WhereClause.MORE);
			whereParam2.setParam(currentDate);

			query = whereParam2.generateClause();
		}

		return query;

	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
