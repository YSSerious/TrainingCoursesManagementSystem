package ua.ukma.nc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import ua.ukma.nc.validator.CategoryValidator;

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
    @Autowired
    private CategoryValidator categoryValidator;

    @RequestMapping("/category")
    public ModelAndView getUser() {
        ModelAndView model = new ModelAndView("category");
        List<CategoryDto> categories = new ArrayList<>();
        for (Category category : categoryService.getAll()) {
            categories.add(new CategoryDto(category));
        }
        model.addObject("categories", categories);
        model.addObject("title", "Categories");
        return model;
    }

    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addCategory(@RequestParam String name, @RequestParam String description) {
        if (!categoryValidator.checkCategoryName(name) && !categoryValidator.checkCategoryDescription(description)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation failed");
        }
        if (categoryService.isExist(name)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Category with this name already exist");
        }
        categoryService.createCategory(new CategoryImpl(name, description));
        Category created = categoryService.getByName(name);
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(created.getId());
        categoryDto.setName(created.getName());
        categoryDto.setDescription(created.getDescription());
        return ResponseEntity.ok().body(categoryDto);
    }

    @RequestMapping(value = "/saveCriteria", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity saveCriteria(@RequestParam Long categoryId, @RequestParam String name) {
        if (!categoryValidator.checkCriteriaName(name)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation failed");
        }
        if (criterionService.isExist(name)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Criteria with this name already exist");
        }
        Criterion criterion = new CriterionImpl(name);
        criterion.setCategory(categoryService.getById(categoryId));
        criterionService.createCriterion(criterion);
        Criterion created = criterionService.getByName(name);
        CriterionDto criterionDto = new CriterionDto();
        criterionDto.setId(created.getId());
        criterionDto.setTitle(created.getTitle());
        criterionDto.setCategoryId(created.getCategory().getId());
        return ResponseEntity.ok().body(criterionDto);
    }


    @RequestMapping(value = "/deleteCriteria", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity deleteCriteria(@RequestParam Long criteriaId) {
        if (criterionService.isExistInProjects(criteriaId))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Criteria is used in some projects, and cannot be deleted");
        int check = criterionService.deleteCriterion(criteriaId);
        if (check == 1)
            return ResponseEntity.ok().body("Criteria was deleted successfully");
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Fail during deleting criteria");
    }

    @RequestMapping(value = "/deleteCategory", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity deleteCategory(@RequestParam Long categoryId) {
        Category category = categoryService.getById(categoryId);
        if (isCriteriaUsing(category))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Several criteria from this category used in some projects, so this category cannot be deleted");
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().body("Success");
    }

    @RequestMapping(value = "/editCategory", method = RequestMethod.POST)
    @ResponseBody
    public CategoryDto editCategory(@RequestParam Long id, @RequestParam String name, @RequestParam String description) {
        int check = categoryService.updateCategory(new CategoryImpl(id, name, description));
        if (check == 1)
            return new CategoryDto(name, description);
        return null;
    }

    private boolean isCriteriaUsing(Category category) {
        for (Criterion criterion : category.getCriteria()) {
            if (criterionService.isExistInProjects(criterion.getId()))
                return true;
        }
        return false;
    }
}
