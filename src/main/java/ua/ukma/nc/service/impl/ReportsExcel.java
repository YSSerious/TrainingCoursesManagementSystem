package ua.ukma.nc.service.impl;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * Created by Alex_Frankiv on 25.11.2016.
 */
public class ReportsExcel extends AbstractXlsView{
    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        Long[] projectIds = (Long[]) map.get("projectIds");

        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\"projects_report_"+new Date(new Timestamp(System.currentTimeMillis()).getTime()).toString()+".xls\"");
        createSheet(workbook, projectIds);
    }

    private void createSheet(Workbook workbook, Long[] projectIds){

    }
}
