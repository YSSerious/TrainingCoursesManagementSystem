package ua.ukma.nc.util.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ua.ukma.nc.vo.AjaxResponse;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(MultipartException.class)
	@ResponseBody
    public AjaxResponse handleMaxUploadException(Exception e){
        AjaxResponse response = new AjaxResponse();
        
        response.addMessage("file", messageSource.getMessage("fail.size", null, LocaleContextHolder.getLocale()));
        response.setCode("204");
        
        return response;
    }
}