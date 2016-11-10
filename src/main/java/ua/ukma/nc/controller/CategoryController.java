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

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String my(@RequestParam String name, @RequestParam int age) {
        System.out.println(name+" "+age);
        return "NICE++++";
    }

    @RequestMapping(value = "/getAllCategory", method = RequestMethod.GET)
    public List<CategoryDto> getAllCategory() {
        List<CategoryDto> categories = new ArrayList<>();
        for(Category category: categoryService.getAll()){
            categories.add(new CategoryDto(category));
        }
        return categories;
    }

}
