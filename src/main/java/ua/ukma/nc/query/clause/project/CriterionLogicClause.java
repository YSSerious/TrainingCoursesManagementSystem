package ua.ukma.nc.query.clause.project;

import ua.ukma.nc.query.clause.general.Clause;

public class CriterionLogicClause implements Clause {
	
	private Long criterionId;

	public CriterionLogicClause(Long criterionId) {
		this.criterionId = criterionId;
	}

	public Long getCriterionId() {
		return criterionId;
	}

	public void setCriterionId(Long criterionId) {
		this.criterionId = criterionId;
	}

	@Override
	public String generateClause() {
		if (criterionId == null)
			throw new NullPointerException("criterionId cannot be null");

		return "(SELECT (EXISTS (SELECT * FROM tcms.project_criterion WHERE id_project = tcms.project.id AND id_criterion="
				+ criterionId + ")))";

	}
}
