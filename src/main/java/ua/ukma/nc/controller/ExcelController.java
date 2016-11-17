package ua.ukma.nc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ua.ukma.nc.service.StudentService;
import ua.ukma.nc.service.UserService;

@Controller
public class ExcelController {

	@Autowired
	private UserService userService;

	@Autowired
	private StudentService studentService;

	@RequestMapping(value = "/studentMarks/{projectId}/{studentId}", method = RequestMethod.GET)
	public ModelAndView downloadExcel(@PathVariable("projectId") Long projectId,
			@PathVariable("studentId") Long studentId) {
		if (userService.canView(studentId))
			return new ModelAndView("excelBuilder", "studentProfile",
					studentService.generateStudentProfile(studentId, projectId));
		return null;
	}
}
