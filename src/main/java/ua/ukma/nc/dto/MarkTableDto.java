package ua.ukma.nc.dto;

import java.util.List;
import java.util.Map;

public class MarkTableDto {
	private List<String> meetings;
	
	private List<CategoryResult> tableData;

	public List<String> getMeetings() {
		return meetings;
	}

	public void setMeetings(List<String> meetings) {
		this.meetings = meetings;
	}

	public List<CategoryResult> getTableData() {
		return tableData;
	}

	public void setTableData(List<CategoryResult> dataTable) {
		this.tableData = dataTable;
	}
}
