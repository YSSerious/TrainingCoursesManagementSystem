package ua.ukma.nc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.ukma.nc.dto.CategoryDto;
import ua.ukma.nc.entity.Category;
import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.impl.real.CategoryImpl;
import ua.ukma.nc.entity.impl.real.CriterionImpl;
import ua.ukma.nc.service.CategoryService;
import ua.ukma.nc.service.CriterionService;

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
    public String addCategory(@RequestParam String name, @RequestParam String description) {
        int check = categoryService.createCategory(new CategoryImpl(name, description));
        if(check==1)
            return "Category was added successfully";
        return "fail";
    }


    @RequestMapping(value = "/deleteCriteria", method = RequestMethod.POST)
    @ResponseBody
    public String deleteCriteria(@RequestParam Long criteriaId) {
        int check = criterionService.deleteCriterion(criteriaId);
        if(check==1)
        return "Criteria was deleted successfully";
        return "fail";
    }

    @RequestMapping(value = "/deleteCategory", method = RequestMethod.POST)
    @ResponseBody
    public String deleteCategory(@RequestParam Long categoryId) {
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

    @RequestMapping(value = "/saveCriteria", method = RequestMethod.POST)
    @ResponseBody
    public String saveCriteria(@RequestParam Long categoryId, @RequestParam String name) {
        Criterion criterion = new CriterionImpl(name);
        criterion.setCategory(categoryService.getById(categoryId));
        int check = criterionService.createCriterion(criterion);
        if(check==1)
        return "Criteria was added successfully";
        return "fail";
    }
}
