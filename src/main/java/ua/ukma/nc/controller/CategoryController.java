package ua.ukma.nc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.ukma.nc.dao.CriterionDao;
import ua.ukma.nc.dto.CategoryDto;
import ua.ukma.nc.dto.CriterionDto;
import ua.ukma.nc.entity.Category;
import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.impl.real.CategoryImpl;
import ua.ukma.nc.entity.impl.real.CriterionImpl;
import ua.ukma.nc.service.CategoryService;
import ua.ukma.nc.service.CriterionService;
import ua.ukma.nc.util.exception.CriteriaDeleteException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Алексей on 08.11.2016.
 */
@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CriterionService criterionService;

    @RequestMapping("/category")
    public ModelAndView getUser() {
        ModelAndView model = new ModelAndView("category");
        List<CategoryDto> categories = new ArrayList<>();
        for(Category category: categoryService.getAll()){
            categories.add(new CategoryDto(category));
        }
            model.addObject("categories",categories);
        return model;
    }

    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    @ResponseBody
    public CategoryDto addCategory(@RequestParam String name, @RequestParam String description) {
        categoryService.createCategory(new CategoryImpl(name, description));
        Category created = categoryService.getByName(name);
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(created.getId());
        categoryDto.setName(created.getName());
        categoryDto.setDescription(created.getDescription());
        return categoryDto;
    }

    @RequestMapping(value = "/saveCriteria", method = RequestMethod.POST)
    @ResponseBody
    public CriterionDto saveCriteria(@RequestParam Long categoryId, @RequestParam String name) {
        Criterion criterion = new CriterionImpl(name);
        criterion.setCategory(categoryService.getById(categoryId));
        criterionService.createCriterion(criterion);
        Criterion created=criterionService.getByName(name);
        CriterionDto criterionDto = new CriterionDto();
        criterionDto.setId(created.getId());
        criterionDto.setTitle(created.getTitle());
        criterionDto.setCategoryId(created.getCategory().getId());
        return criterionDto;
    }


    @RequestMapping(value = "/deleteCriteria", method = RequestMethod.POST)
    @ResponseBody
    public String deleteCriteria(@RequestParam Long criteriaId) throws CriteriaDeleteException {
        if(criterionService.isExistInProjects(criteriaId))
            throw new CriteriaDeleteException("Criteria is used in some projects, and cannot be deleted");
        int check = criterionService.deleteCriterion(criteriaId);
        if(check==1)
        return "Criteria was deleted successfully";
        return "fail";
    }

    @RequestMapping(value = "/deleteCategory", method = RequestMethod.POST)
    @ResponseBody
    public String deleteCategory(@RequestParam Long categoryId) throws CriteriaDeleteException {
        Category category= categoryService.getById(categoryId);
        if(isCriteriaUsing(category))
            throw new CriteriaDeleteException("Criteria from this category used in some projects, so this category cannot be deleted");
        categoryService.deleteCategory(categoryId);
        return "Category was deleted successfully";
    }

    @RequestMapping(value = "/editCategory", method = RequestMethod.POST)
    @ResponseBody
    public CategoryDto editCategory(@RequestParam Long id, @RequestParam String name, @RequestParam String description) {
        int check =categoryService.updateCategory(new CategoryImpl(id, name, description));
        if(check==1)
        return new CategoryDto(name, description);
        return null;
    }

    private boolean isCriteriaUsing(Category category){
        for(Criterion criterion: category.getCriteria()){
        if(criterionService.isExistInProjects(criterion.getId()))
            return true;
        }
        return false;
    }
}
