package ua.ukma.nc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ua.ukma.nc.entity.Category;
import ua.ukma.nc.query.ProjectParamResolver;
import ua.ukma.nc.service.CategoryService;
import ua.ukma.nc.service.ProjectService;

@Controller
public class ProjectsController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private CategoryService categoryService;

	public static final int ITEMS_PER_PAGE = 5;

	public static final String START = "startType";
	public static final String END = "endType";
	public static final String START_AND_END = "startAndEndType";

	public static final String UPCOMING = "upcoming";
	public static final String ACTIVE = "active";
	public static final String COMPLETED = "completed";

	public static final String NAME_ASC = "nameasc";
	public static final String NAME_DESC = "namedesc";
	public static final String START_ASC = "startasc";
	public static final String START_DESC = "startdesc";
	public static final String END_ASC = "finishasc";
	public static final String END_DESC = "finishdesc";

	private static Logger log = LoggerFactory.getLogger(HomeController.class.getName());

	@RequestMapping("/projects")
	public ModelAndView getProjects(@RequestParam(value = "text-search", required = false) String textSearch,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "date-type", required = false) String dateType,
			@RequestParam(value = "status", required = false) String[] statuses,
			@RequestParam(value = "criteria", required = false) Long[] criteria,
			@RequestParam(value = "start-date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam(value = "end-date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

		ModelAndView model = new ModelAndView();
		log.info("Projects information sent");

		model.addObject(ACTIVE, ACTIVE);
		model.addObject(UPCOMING, UPCOMING);
		model.addObject(COMPLETED, COMPLETED);

		ProjectParamResolver projectParamResolver = new ProjectParamResolver();
		projectParamResolver.setCriteria(criteria);

		if (endDate != null)
			projectParamResolver.setEnd(new java.sql.Date(endDate.getTime()));

		if (startDate != null)
			projectParamResolver.setStart(new java.sql.Date(startDate.getTime()));

		projectParamResolver.setOrder(order);
		projectParamResolver.setPage(page);
		projectParamResolver.setStatuses(statuses);
		projectParamResolver.setText(textSearch);
		projectParamResolver.setDateType(dateType);

		if (startDate != null)
			model.addObject("start", new SimpleDateFormat("yyyy-MM-dd").format(startDate));

		if (endDate != null)
			model.addObject("end", new SimpleDateFormat("yyyy-MM-dd").format(endDate));

		model.addObject("textSearch", textSearch);

		if (statuses != null)
			for (String status : statuses) {
				model.addObject(status + "Value", "checked=\"\"");
			}

		model.addObject("currPage", projectParamResolver.getPage());
		model.addObject("path", projectParamResolver.getLine());
		model.addObject("criteria", criteria);

		if (order != null)
			model.addObject(order + "Value", "selected");
		else
			model.addObject("defaultValue", "selected");

		if (dateType != null)
			model.addObject(dateType + "Value", "selected");
		else
			model.addObject("defaultValue", "selected");

		String query = projectParamResolver.getQueryBuilder().generateQuery();
		String count = projectParamResolver.getQueryBuilder().generateCountQuery();
		model.addObject("projects", projectService.query(query));

		int maxPage = projectService.count(count);

		if (maxPage % ITEMS_PER_PAGE == 0 && maxPage != 0)
			maxPage /= ITEMS_PER_PAGE;
		else
			maxPage = maxPage / ITEMS_PER_PAGE + 1;

		model.addObject("maxPage", maxPage);
		model.setViewName("projects");
		return model;
	}

	@RequestMapping("/ajaxcategories")
	@ResponseBody
	public List<Category> ajaxCategories() {

		log.info("Categories information sent (AJAX)");

		return categoryService.getAllAjax();
	}

	@RequestMapping(value = "projects/create-project", method = RequestMethod.GET)
	public ModelAndView showCreateProjectForm(Model model) {
		return new ModelAndView("create-project");
	}
}
