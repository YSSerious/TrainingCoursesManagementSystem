package ua.ukma.nc.dto;

import java.util.List;

public class MarkTableDto {
	private List<String> meetings;
	private List<String> criteria;
	
	private String[][] tableData;

	public List<String> getMeetings() {
		return meetings;
	}

	public void setMeetings(List<String> meetings) {
		this.meetings = meetings;
	}

	public List<String> getCriteria() {
		return criteria;
	}

	public void setCriteria(List<String> criteria) {
		this.criteria = criteria;
	}

	public String[][] getTableData() {
		return tableData;
	}

	public void setTableData(String[][] tableData) {
		this.tableData = tableData;
	}
}
