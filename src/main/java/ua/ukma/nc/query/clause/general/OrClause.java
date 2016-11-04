package ua.ukma.nc.query.clause.general;

import java.util.ArrayList;

public class OrClause implements Clause {

	private ArrayList<String> values;

	public OrClause() {
		values = new ArrayList<String>();
	}

	public void addValue(String value) {
		values.add(value);
	}

	@Override
	public String generateClause() {

		if (values.isEmpty())
			return "";

		String s = "(";

		for (String value : values)
			s += value + " OR ";

		s = s.substring(0, s.length() - 4) + ")";

		return s;
	}

}
