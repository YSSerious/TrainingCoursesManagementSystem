package ua.ukma.nc.service.impl;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import ua.ukma.nc.dto.CertainMarkDto;
import ua.ukma.nc.dto.CriterionResult;
import ua.ukma.nc.dto.StudentProfile;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

public class StudentMarksExcel extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		StudentProfile studentProfile = (StudentProfile) model.get("studentProfile");

		response.setHeader("Content-Disposition", "attachment; filename=\"" + studentProfile.getLastName() + "-"
				+ studentProfile.getProjectName() + ".xls\"");

		Sheet sheet = workbook.createSheet("Marks");

		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("#");

		List<String> meetings = studentProfile.getMarkTableDto().getMeetings();
		for (int i = 1; i < meetings.size() + 1; i++)
			header.createCell(i).setCellValue(meetings.get(i - 1));

		int rowCount = 1;
		Map<String, List<CriterionResult>> marks = studentProfile.getMarkTableDto().getTableData();
		for (String key : marks.keySet()) {

			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, meetings.size()));
			Row keyRow = sheet.createRow(rowCount++);
			keyRow.createCell(0).setCellValue(key);

			List<CriterionResult> criterionResults = marks.get(key);

			for (CriterionResult criterionResult : criterionResults) {
				Row markRow = sheet.createRow(rowCount++);
				markRow.createCell(0).setCellValue(criterionResult.getCriterionName());

				int column = 1;

				for (CertainMarkDto mark : criterionResult.getMarks()) {
					markRow.createCell(column++).setCellValue(mark.getValue());
				}
			}
		}
	}
}