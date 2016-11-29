package ua.ukma.nc.view.excel;

import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import ua.ukma.nc.dto.CategoryResult;
import ua.ukma.nc.dto.CertainMarkDto;
import ua.ukma.nc.dto.CriterionResult;
import ua.ukma.nc.dto.MarkTableDto;
import ua.ukma.nc.dto.MeetingReviewDto;
import ua.ukma.nc.dto.StudentProfile;
import ua.ukma.nc.dto.StudentStatusLog;

public class SheetBuilder {

	private static final int WIDTH = 4000;
	private static final int FINAL_REVIEW_WIDTH = 6;

	public void createSheet(Workbook workbook, StudentProfile studentProfile, String name) {

		MarkTableDto markTableDto = studentProfile.getMarkTableDto();

		Sheet sheet = workbook.createSheet(name);
		
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setColor(IndexedColors.GREY_50_PERCENT.index);
		style.setFont(font);
		
		CellStyle noMarkStyle = workbook.createCellStyle();
		noMarkStyle.setFillForegroundColor(IndexedColors.RED.index);
		noMarkStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		CellStyle finalReviewStyle = workbook.createCellStyle();
		font = workbook.createFont();
		font.setColor(IndexedColors.RED.index);
		finalReviewStyle.setFont(font);

		CellStyle categoryStyle = workbook.createCellStyle();
		categoryStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		categoryStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		font = workbook.createFont();
		font.setBold(true);
		categoryStyle.setFont(font);

		CellStyle headerStyle = workbook.createCellStyle();
		font = workbook.createFont();
		font.setBold(true);
		headerStyle.setFont(font);
		Row projectNameRow = sheet.createRow(0);
		projectNameRow.createCell(0).setCellValue("Project name");
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 4));
		projectNameRow.createCell(1).setCellValue(studentProfile.getProjectName());
		
		Row studentLastNameRow = sheet.createRow(1);
		studentLastNameRow.createCell(0).setCellValue("Last name");
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 4));
		studentLastNameRow.createCell(1).setCellValue(studentProfile.getLastName());

		Row studentFirstNameRow = sheet.createRow(2);
		studentFirstNameRow.createCell(0).setCellValue("First name");
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 4));
		studentFirstNameRow.createCell(1).setCellValue(studentProfile.getFirstName());
		
		Row studentSecondNameRow = sheet.createRow(3);
		studentSecondNameRow.createCell(0).setCellValue("Second name");
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 4));
		studentSecondNameRow.createCell(1).setCellValue(studentProfile.getSecondName());
		
		Row studentEmailRow = sheet.createRow(4);
		studentEmailRow.createCell(0).setCellValue("Email");
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 4));
		studentEmailRow.createCell(1).setCellValue(studentProfile.getEmail());
		
		Row meetingHeader = sheet.createRow(6);
		meetingHeader.createCell(0).setCellValue("#");
		meetingHeader.getCell(0).setCellStyle(headerStyle);
		sheet.setColumnWidth(0, WIDTH * 2);

		List<MeetingReviewDto> meetings = markTableDto.getMeetings();
		for (int i = 1; i < meetings.size() * 2; i += 2) {
			sheet.setColumnWidth(i, WIDTH);
			sheet.setColumnWidth(i + 1, WIDTH);

			meetingHeader.createCell(i).setCellValue(meetings.get(i / 2).getDate());
			meetingHeader.createCell(i + 1).setCellValue("Commentary");

			meetingHeader.getCell(i).setCellStyle(headerStyle);
			meetingHeader.getCell(i + 1).setCellStyle(headerStyle);
		}

		meetingHeader.createCell(meetings.size() * 2 + 1).setCellValue("Final Review");
		meetingHeader.createCell(meetings.size() * 2 + 2).setCellValue("Commentary");
		meetingHeader.getCell(meetings.size() * 2 + 1).setCellStyle(headerStyle);
		meetingHeader.getCell(meetings.size() * 2 + 2).setCellStyle(headerStyle);
		sheet.setColumnWidth(meetings.size() * 2 + 1, WIDTH);
		sheet.setColumnWidth(meetings.size() * 2 + 2, WIDTH);

		int rowCount = 7;
		List<CategoryResult> marks = markTableDto.getTableData();
		for (CategoryResult key : marks) {

			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, meetings.size() * 2 + 2));
			Row keyRow = sheet.createRow(rowCount++);
			keyRow.createCell(0).setCellValue(key.getCategoryDto().getName());
			keyRow.getCell(0).setCellStyle(categoryStyle);

			List<CriterionResult> criterionResults = key.getCriteriaResults();

			for (CriterionResult criterionResult : criterionResults) {
				Row markRow = sheet.createRow(rowCount++);
				markRow.createCell(0).setCellValue(criterionResult.getCriterionName());
				markRow.getCell(0).setCellStyle(headerStyle);
				int column = 1;

				for (CertainMarkDto mark : criterionResult.getMarks()) {
					String stringMark = mark.getValue();
					try {
						markRow.createCell(column).setCellValue(Integer.valueOf(stringMark));
					} catch (Exception e) {
						markRow.createCell(column);
						
						if(stringMark.equals("-"))
							markRow.getCell(column).setCellStyle(noMarkStyle);
					}
					column++;
					markRow.createCell(column++).setCellValue(mark.getCommentary());
					markRow.getCell(column - 1).setCellStyle(style);
				}

				markRow.getCell(column - 1).setCellStyle(finalReviewStyle);
				markRow.getCell(column - 2).setCellStyle(finalReviewStyle);
			}
		}

		rowCount += 3;
		Row keyRow = sheet.createRow(rowCount++);
		keyRow.setHeight((short) 1000);
		keyRow.createCell(0).setCellValue("Final Review");
		keyRow.getCell(0).setCellStyle(headerStyle);
		sheet.addMergedRegion(new CellRangeAddress(rowCount - 1, rowCount - 1, 1, FINAL_REVIEW_WIDTH));
		if (studentProfile.getMarkTableDto().getFinalReview() != null)
			keyRow.createCell(1).setCellValue(studentProfile.getMarkTableDto().getFinalReview().getCommentary());
		else
			keyRow.createCell(1).setCellValue("No final review.");

		keyRow = sheet.createRow(rowCount++);
		keyRow.setHeight((short) 1000);
		keyRow.createCell(0).setCellValue("General Review");
		keyRow.getCell(0).setCellStyle(headerStyle);
		sheet.addMergedRegion(new CellRangeAddress(rowCount - 1, rowCount - 1, 1, FINAL_REVIEW_WIDTH));
		if (studentProfile.getGeneralReview() != null)
			keyRow.createCell(1).setCellValue(studentProfile.getGeneralReview().getCommentary());
		else
			keyRow.createCell(1).setCellValue("No general review.");

		keyRow = sheet.createRow(rowCount++);
		keyRow.setHeight((short) 1000);
		keyRow.createCell(0).setCellValue("Technical Review");
		keyRow.getCell(0).setCellStyle(headerStyle);
		sheet.addMergedRegion(new CellRangeAddress(rowCount - 1, rowCount - 1, 1, FINAL_REVIEW_WIDTH));
		if (studentProfile.getTechnicalReview() != null)
			keyRow.createCell(1).setCellValue(studentProfile.getTechnicalReview().getCommentary());
		else
			keyRow.createCell(1).setCellValue("No technical review.");

		rowCount += 3;
		keyRow = sheet.createRow(rowCount++);
		sheet.addMergedRegion(new CellRangeAddress(rowCount - 1, rowCount - 1, 1, 2));
		sheet.addMergedRegion(new CellRangeAddress(rowCount - 1, rowCount - 1, 3, 6));
		keyRow.createCell(0).setCellValue("Status");
		keyRow.getCell(0).setCellStyle(headerStyle);
		keyRow.createCell(1).setCellValue("Date");
		keyRow.getCell(1).setCellStyle(headerStyle);
		keyRow.createCell(3).setCellValue("Comentary");
		keyRow.getCell(3).setCellStyle(headerStyle);

		for (StudentStatusLog studentStatus : studentProfile.getStudentStatuses()) {
			Row markRow = sheet.createRow(rowCount++);
			sheet.addMergedRegion(new CellRangeAddress(rowCount - 1, rowCount - 1, 1, 2));
			sheet.addMergedRegion(new CellRangeAddress(rowCount - 1, rowCount - 1, 3, 6));
			markRow.createCell(0).setCellValue(studentStatus.getStatusDescription());
			markRow.createCell(1).setCellValue(studentStatus.getDate());
			markRow.createCell(3).setCellValue(studentStatus.getCommentary());
		}
	}
}
