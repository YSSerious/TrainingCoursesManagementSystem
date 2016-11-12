package ua.ukma.nc.dto;

import java.util.List;
import java.util.Map;

public class MarkTableDto {
	private List<String> meetings;
	
	private Map<String, String[][]> tableData;

	public List<String> getMeetings() {
		return meetings;
	}

	public void setMeetings(List<String> meetings) {
		this.meetings = meetings;
	}

	public Map<String, String[][]> getTableData() {
		return tableData;
	}

	public void setTableData(Map<String, String[][]> dataTable) {
		this.tableData = dataTable;
	}
}
