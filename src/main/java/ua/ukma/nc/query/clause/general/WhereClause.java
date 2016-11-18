package ua.ukma.nc.query.clause.general;

import java.util.Date;

public class WhereClause implements Clause {
	public static final String EQUALS = "=";
	public static final String MORE = ">";
	public static final String LESS = "<";
	public static final String STRING_CONTAINS = "LIKE";

	private String operation;
	private Object param;
	private String column;

	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		this.param = param;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String generateClause() {

		if (column == null)
			throw new NullPointerException("Column can't be null!");

		if (param == null)
			throw new NullPointerException("Param can't be null!");

		if (operation == null)
			throw new NullPointerException("Operation can't be null!");

		if (param instanceof String || param instanceof Date) {

			if (param instanceof String && operation.equals(STRING_CONTAINS))
				return "UPPER("+column + ") " + operation + " " + "UPPER('%" + param + "%')";
			else if (operation.equals(STRING_CONTAINS))
				throw new IllegalArgumentException("Wrong operation!");

			return column + " " + operation + " " + "'" + param + "'";

		} else if (param instanceof Long || param instanceof Integer) {
			String value = String.valueOf(param);

			if (operation.equals(STRING_CONTAINS))
				throw new IllegalArgumentException("Wrong operation!");

			return column + " " + operation + " " + value;
		}

		throw new IllegalArgumentException("Wrong type!");
	}
}
