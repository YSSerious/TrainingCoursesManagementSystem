package ua.ukma.nc.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import ua.ukma.nc.vo.ProjectVo;

@Controller
public class ProjectsController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProjectFormValidator projectFromValidator;
	
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
	
	@InitBinder("projectForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(projectFromValidator);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(true);
                binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	
	@RequestMapping(value = "/projects/create-project", method = RequestMethod.GET)
	public String showCreateProjectForm(Model model) {
		Project project = new ProjectImpl();
		model.addAttribute("projectForm", project);
		return "create-project";
	}
	
	@RequestMapping(value = "/projects/create-project", method = RequestMethod.POST)
	public String createProject(@ModelAttribute("projectForm") @Validated ProjectImpl project,
            BindingResult result, final RedirectAttributes redirectAttributes) {
            if (!result.hasErrors()) {
                projectService.createProject(project);
                redirectAttributes.addFlashAttribute("msg", "Project added successfully!");
                return "redirect:/projects";
            } else {
                return "create-project";
            }
	}
	
        @ResponseBody
        @RequestMapping(value = "/projects/create-project.ajax", method = RequestMethod.POST)
        public String createProject(@RequestBody ProjectVo projectVo) {
            Project project = new ProjectImpl(projectVo.getName(), projectVo.getDescription(), projectVo.getStartDate(), projectVo.getFinishDate());
            projectService.createProject(project);
            return "";
        }
        
	@RequestMapping(value = "/projects/create-project/is-name-free")
	public @ResponseBody String isNameFree(@RequestBody String name){
		String result = "";
		String nname = new String(name);
		Project project = projectService.getByName(nname.trim());
		if (project == null){
			result = "true";
		}
		else {
			result = "false";
		}
		return result;
	}
}
