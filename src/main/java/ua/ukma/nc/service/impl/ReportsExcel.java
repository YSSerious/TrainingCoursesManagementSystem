package ua.ukma.nc.service.impl;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;
import ua.ukma.nc.dto.ProjectReportItemDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Alex_Frankiv on 25.11.2016.
 */
public class ReportsExcel extends AbstractXlsView{
    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        List<ProjectReportItemDto> data = (List<ProjectReportItemDto>) map.get("data");

        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\"projects_report_"+new Date(new Timestamp(System.currentTimeMillis()).getTime()).toString()+".xls\"");
        createSheet(workbook, data);
    }

    private void createSheet(Workbook workbook, List<ProjectReportItemDto> data){
        Sheet sheet = workbook.createSheet();

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Projects");
        header.createCell(1).setCellValue("Started");
        header.createCell(2).setCellValue("Left");
        header.createCell(3).setCellValue("Not invited to interview");
        header.createCell(4).setCellValue("Invited to interview");
        header.createCell(5).setCellValue("Job offers");

        int rows = 1;
        for(ProjectReportItemDto item : data){
            Row row = sheet.createRow(rows++);

            row.createCell(0).setCellValue(item.getProjectName());
            row.createCell(1).setCellValue(item.getNumOfStarted());
            row.createCell(2).setCellValue(item.getNumOfLeft());
            row.createCell(3).setCellValue(item.getNumOfNotInvited());
            row.createCell(4).setCellValue(item.getNumOfWasInvited());
            row.createCell(5).setCellValue(item.getNumOfJobOffer());
        }
    }
}
