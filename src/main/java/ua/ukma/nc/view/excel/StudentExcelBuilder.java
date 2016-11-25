package ua.ukma.nc.view.excel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import ua.ukma.nc.dto.StudentProfile;
import ua.ukma.nc.dto.UserDto;

import org.apache.poi.ss.usermodel.Workbook;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;
import java.util.Map.Entry;

public class StudentExcelBuilder extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		SheetBuilder sheetBuilder = new SheetBuilder();
		
		Map<String, StudentProfile> values = (Map<String, StudentProfile>) model.get("values");
		String title = (String) model.get("title");
		
		response.setHeader("Content-Disposition", "attachment; filename=\"" + "student-"+ title + ".xls\"");
		
		for(Entry<String, StudentProfile> entry: values.entrySet())
			sheetBuilder.createSheet(workbook, entry.getValue(), entry.getKey());
	}
	
	
}