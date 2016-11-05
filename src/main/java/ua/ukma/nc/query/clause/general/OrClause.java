package ua.ukma.nc.query.clause.general;

import java.util.ArrayList;

public class OrClause implements Clause {

	private ArrayList<Clause> values;

	public OrClause() {
		values = new ArrayList<Clause>();
	}

	public void addValue(Clause value) {
		values.add(value);
	}

	@Override
	public String generateClause() {

		if (values == null || values.isEmpty())
			throw new NullPointerException("Values cannot be null");

		String s = "(";

		for (Clause value : values)
			s += value.generateClause() + " OR ";

		s = s.substring(0, s.length() - 4) + ")";

		return s;
	}

}
