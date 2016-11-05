package ua.ukma.nc.query.clause.project;

import ua.ukma.nc.query.clause.general.Clause;

public class MentorLogicClause implements Clause{
	
	private String email;
	
	public MentorLogicClause(String email){
		this.setEmail(email);
	}
	
	@Override
	public String generateClause() {
		return "(SELECT (EXISTS (SELECT * FROM tcms.group WHERE id_project = tcms.project.id AND id IN (SELECT id_group FROM tcms.user_group WHERE id_user IN (SELECT id FROM tcms.user WHERE email = '"+email+"')))))";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
