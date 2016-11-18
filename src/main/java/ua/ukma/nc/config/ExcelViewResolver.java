package ua.ukma.nc.config;
import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import ua.ukma.nc.service.impl.StudentMarksExcel;
 
public class ExcelViewResolver implements ViewResolver{
 
    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
    	StudentMarksExcel view = new StudentMarksExcel();
        return view;
      }
     
}