package ua.ukma.nc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.ukma.nc.dto.CategoryDto;
import ua.ukma.nc.entity.Category;
import ua.ukma.nc.entity.impl.real.CategoryImpl;
import ua.ukma.nc.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Алексей on 08.11.2016.
 */
@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

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
        System.out.println(name+" "+description);
        return "Category was added successfully";
    }


    @RequestMapping(value = "/deleteCriteria", method = RequestMethod.POST)
    @ResponseBody
    public String deleteCriteria(@RequestParam Long criteriaId) {
        System.out.println(criteriaId);
        return "Criteria was deleted successfully";
    }

    @RequestMapping(value = "/deleteCategory", method = RequestMethod.POST)
    @ResponseBody
    public String deleteCategory(@RequestParam Long categoryId) {
        System.out.println(categoryId);
        return "Category was deleted successfully";
    }

    @RequestMapping(value = "/editCategory", method = RequestMethod.POST)
    @ResponseBody
    public CategoryDto editCategory(@RequestParam Long id, @RequestParam String name, @RequestParam String description) {
        System.out.println(id+" "+name+" "+ description);
        return new CategoryDto(name, description);
    }

    @RequestMapping(value = "/saveCriteria", method = RequestMethod.POST)
    @ResponseBody
    public String saveCriteria(@RequestParam Long categoryId, @RequestParam String name) {
        System.out.println(categoryId+" "+name);
        return "Criteria was added successfully";
    }
}
