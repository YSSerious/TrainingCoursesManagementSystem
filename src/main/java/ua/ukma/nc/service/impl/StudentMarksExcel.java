package ua.ukma.nc.service.impl;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import ua.ukma.nc.dto.CategoryResult;
import ua.ukma.nc.dto.CertainMarkDto;
import ua.ukma.nc.dto.CriterionResult;
import ua.ukma.nc.dto.MarkTableDto;
import ua.ukma.nc.dto.MeetingReviewDto;
import ua.ukma.nc.dto.UserDto;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class StudentMarksExcel extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String, MarkTableDto> values = (Map<String, MarkTableDto>) model.get("values");
		UserDto student = (UserDto) model.get("student");
		
		response.setHeader("Content-Disposition", "attachment; filename=\"" + "student-"+ student.getLastName()+"-"+ student.getFirstName() + ".xls\"");
		
		for(Entry<String, MarkTableDto> entry: values.entrySet())
			createSheet(workbook, entry.getValue(), entry.getKey());
	}
	
	private void createSheet(Workbook workbook, MarkTableDto markTableDto, String name){
		Sheet sheet = workbook.createSheet(name);

		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("#");

		List<MeetingReviewDto> meetings = markTableDto.getMeetings();
		for (int i = 1; i < meetings.size() + 1; i++)
			header.createCell(i).setCellValue(meetings.get(i - 1).getName());
		
		header.createCell(meetings.size() + 1).setCellValue("Final Review");

		int rowCount = 1;
		List<CategoryResult> marks = markTableDto.getTableData();
		for (CategoryResult key : marks) {

			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, meetings.size()));
			Row keyRow = sheet.createRow(rowCount++);
			keyRow.createCell(0).setCellValue(key.getCategoryDto().getName());

			List<CriterionResult> criterionResults = key.getCriteriaResults();

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