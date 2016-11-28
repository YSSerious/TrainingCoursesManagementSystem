package ua.ukma.nc.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ua.ukma.nc.dto.CategoryDto;
import ua.ukma.nc.dto.ProjectDto;
import ua.ukma.nc.entity.Project;
import ua.ukma.nc.entity.impl.real.ProjectImpl;
import ua.ukma.nc.query.ProjectSearch;
import ua.ukma.nc.service.CategoryService;
import ua.ukma.nc.service.ProjectService;
import ua.ukma.nc.validator.ProjectFormValidator;
import ua.ukma.nc.vo.AjaxResponse;
import ua.ukma.nc.vo.ProjectVo;

@Controller
public class ProjectsController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProjectFormValidator projectFromValidator;
        
        @Autowired
        private MessageSource messageSource;
	
	private static Logger log = LoggerFactory.getLogger(HomeController.class.getName());
	
	@RequestMapping("/projects")
	public ModelAndView getProjects(@ModelAttribute("projectSearch") ProjectSearch projectSearch) {

		ModelAndView model = new ModelAndView();

		model.addObject("currPage", projectSearch.getPage());
		model.addObject("criteria", projectSearch.getCriteria());
		model.addObject("projects", projectService.search(projectSearch).stream().map(ProjectDto::new).collect(Collectors.toList()));
		model.addObject("maxPage", projectService.getMaxPage(projectSearch));
		model.addObject("title", "All projects");

		model.setViewName("projects");
		log.info("Projects information sent");
		return model;
	}

	@RequestMapping("/ajaxcategories")
	@ResponseBody
	public List<CategoryDto> ajaxCategories() {
		log.info("Categories information sent (AJAX)");
		return categoryService.getAll().stream().map(CategoryDto::new).collect(Collectors.toList());
	}
	
	@RequestMapping(value = "/projects/add", method = RequestMethod.POST)
        @ResponseBody
	public AjaxResponse createProject(@RequestBody @Validated ProjectVo projectVo) throws InterruptedException {
            DataBinder dataBinder = new WebDataBinder(projectVo);
            dataBinder.setValidator(projectFromValidator);
            dataBinder.validate();
            BindingResult result = dataBinder.getBindingResult();
            AjaxResponse response = new AjaxResponse();
            if (!result.hasErrors()) {
                Project project = new ProjectImpl(
                        projectVo.getName(), projectVo.getDescription(),
                        projectVo.getStartDate(), projectVo.getFinishDate());
                projectService.createProject(project);
                response.setCode("200");
            } else {
                response.setCode("204");
                result.getFieldErrors().stream().forEach((FieldError error) -> {
                    response.addMessage(error.getField(),
                            messageSource.getMessage(error.getCode(),
                                    null, LocaleContextHolder.getLocale()));
                });
            }
            return response;
	}

}
