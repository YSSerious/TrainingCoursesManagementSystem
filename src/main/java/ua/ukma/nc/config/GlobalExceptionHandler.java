package ua.ukma.nc.config;

import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import ua.ukma.nc.controller.HomeController;
import ua.ukma.nc.vo.AjaxResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends DefaultHandlerExceptionResolver {

	private static Logger log = LoggerFactory.getLogger(HomeController.class.getName());

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(MultipartException.class)
	@ResponseBody
	public AjaxResponse handleMaxUploadException(Exception e) {
		AjaxResponse response = new AjaxResponse();

		response.addMessage("file", messageSource.getMessage("fail.size", null, LocaleContextHolder.getLocale()));
		response.setCode("204");

		return response;
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView handleNotFoundException(Exception ex) {
		ModelAndView modelAndView = new ModelAndView("error/404");
		modelAndView.setStatus(HttpStatus.NOT_FOUND);
		log.error(ex.getMessage());
		return new ModelAndView("error/404");
	}
	
	@ExceptionHandler(PSQLException.class)
	public ModelAndView handlePSQLException(Exception ex){
		ModelAndView modelAndView = new ModelAndView("error/psql");
		modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		log.error(ex.getMessage());
		return new ModelAndView("error/psql");
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ModelAndView handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
		ModelAndView modelAndView = new ModelAndView("error/noArgument");
		modelAndView.setStatus(HttpStatus.BAD_REQUEST);
		log.error(ex.getMessage());
		return modelAndView;
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ModelAndView handleEmpyResultDataAccessException(EmptyResultDataAccessException ex) {
		ModelAndView modelAndView = new ModelAndView("error/emptyData");
		modelAndView.setStatus(HttpStatus.NOT_FOUND);
		log.error(ex.getMessage());
		return modelAndView;
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleDefaultException(EmptyResultDataAccessException ex) {
		ModelAndView modelAndView = new ModelAndView("error/defaultError");
		modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		log.error(ex.getMessage());
		return modelAndView;
	}
}