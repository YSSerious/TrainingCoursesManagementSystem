package ua.ukma.nc.config;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import ua.ukma.nc.view.excel.ReportsExcel;
import ua.ukma.nc.view.excel.GroupExcelBuilder;
import ua.ukma.nc.view.excel.ProjectExcelBuilder;
import ua.ukma.nc.view.excel.StudentExcelBuilder;


public class ExcelViewResolver implements ViewResolver {

	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		if (viewName.equals("studentExcelBuilder"))
			return new StudentExcelBuilder();
		if (viewName.equals("projectExcelBuilder"))
			return new ProjectExcelBuilder();
		if (viewName.equals("groupExcelBuilder"))
			return new GroupExcelBuilder();
		if (viewName.equals("excelProjectReports"))
            return new ReportsExcel();
		return null;
	}

}