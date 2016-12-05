package ua.ukma.nc.controller;

import java.util.ArrayList;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import ua.ukma.nc.dto.*;
import ua.ukma.nc.entity.*;
import ua.ukma.nc.service.*;
import ua.ukma.nc.util.exception.CriteriaDeleteException;
import ua.ukma.nc.validator.NewMeetingValidator;
import ua.ukma.nc.validator.ProjectAttachmentFormValidator;
import ua.ukma.nc.vo.AjaxResponse;

@Controller
public class CertainProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CriterionService criterionService;

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private ProjectAttachmentService attachmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectAttachmentFormValidator projectAttachmentFormValidator;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private FinalReviewCriterionService finalReviewCriterionService;

    @Autowired
    private NewMeetingValidator meetingValidator;

    @InitBinder("projectAttachmentForm")
    protected void initBinderFileBucket(WebDataBinder binder) {
        binder.setValidator(projectAttachmentFormValidator);
    }

    @RequestMapping(value = "/certainProject/{id}", method = RequestMethod.GET)
    public ModelAndView viewProject(@PathVariable("id") Long id) {
        ModelAndView model = new ModelAndView();

        List<CategoryDto> categories = categoryService.getByProjectId(id).stream().map(CategoryDto::new)
                .collect(Collectors.toList());

        List<CriterionDto> criteria = criterionService.getByProject(id).stream().map(CriterionDto::new)
                .collect(Collectors.toList());

        List<UserDto> students = userService.studentsByProjectId(id).stream().map(UserDto::new)
                .collect(Collectors.toList());

        model.addObject("projectAttachmentForm", new ProjectAttachmentFormDto());
        model.addObject("categories", categories);
        model.addObject("criteria", criteria);
        model.addObject("students", students);

        ProjectDto prDto = new ProjectDto(projectService.getById(id));
        model.addObject("project", prDto);

        //Group set
        List<Group> groupList = groupService.getByProjectId(id);

        List<GroupProjectDto> groupDtos = new ArrayList<>();
		groupList.stream().map((group) -> {
			Meeting upcomingMeeting = meetingService.getUpcomingByGroup(group.getId());
			Long studentsAmount = groupService.getStudentsAmount(group.getId());
			GroupProjectDto groupDto = new GroupProjectDto(group, upcomingMeeting, studentsAmount);
			return groupDto;
		}).forEach((groupDto) -> {
			groupDtos.add(groupDto);
		});
        model.addObject("groups", groupDtos);

        //Criteria set
        List<CriterionDto> criterionDtos = new ArrayList<>();
        for (Criterion criterion : criterionService.getByProject(id)) {
            criterionDtos.add(new CriterionDto(criterion.getId(), criterion.getTitle(), isCriterionRated(id, criterion)));
        }
        model.addObject("criterions", criterionDtos);

        //Attachment set
        List<ProjectAttachment> attachmentList = attachmentService.getAllById(id);
        model.addObject("attachments", attachmentList);

        model.setViewName("certainProject");
		if (groupDtos.isEmpty() && criterionDtos.isEmpty() && attachmentList.isEmpty()) {
			model.addObject("isEmpty", true);
		} else {
			model.addObject("isEmpty", false);
		}
        return model;
    }

    private boolean isCriterionRated(Long id, Criterion criterion) {
        return criterionService.isRatedInProject(id, criterion) || finalReviewCriterionService.isExists(criterion, id);
    }

    @RequestMapping(value = "/updateProjectName", method = RequestMethod.POST)
    @ResponseBody
    public String updateProjectName(
            @RequestParam("projectId") Long projectId,
            @RequestParam("projectName") String projectName) {
        Project project = projectService.getById(projectId);
        project.setName(projectName);
        projectService.updateProject(project);
        return "";
    }

    @RequestMapping(value = "/updateProjectStartDate", method = RequestMethod.POST)
    @ResponseBody
    public String updateProjectStartDate(
            @RequestParam("projectId") Long projectId,
            @RequestParam("projectStartDate") Date startDate) {
        Project project = projectService.getById(projectId);
        project.setStartDate(startDate);
        projectService.updateProject(project);
        return "";
    }

    @RequestMapping(value = "/updateProjectFinishDate", method = RequestMethod.POST)
    @ResponseBody
    public String updateProjectFinishDate(
            @RequestParam("projectId") Long projectId,
            @RequestParam("projectFinishDate") Date finishDate) {
        Project project = projectService.getById(projectId);
        project.setFinishDate(finishDate);
        projectService.updateProject(project);
        return "";
    }

    @RequestMapping(value = "/updateProjectDescription", method = RequestMethod.POST)
    @ResponseBody
    public String updateProjectDescription(
            @RequestParam("projectId") Long projectId,
            @RequestParam("projectDescription") String description) {
        Project project = projectService.getById(projectId);
        project.setDescription(description);
        projectService.updateProject(project);
        return "";
    }

	@RequestMapping(value = "/projects/delete", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse deleteProject(
			@RequestParam("projectId") Long projectId) {
		AjaxResponse response = new AjaxResponse();
		projectService.deleteProject(projectService.getById(projectId));
		response.setCode("200");
		return response;
	}
	
    @RequestMapping(value = "/addProjectAttachment", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse addProjectAttachment(@Valid @ModelAttribute("projectAttachmentForm") ProjectAttachmentFormDto attachmentDto,
                                             BindingResult result) throws IOException {
        AjaxResponse response = new AjaxResponse();

        if (result.hasErrors()) {

            result.getFieldErrors().stream().forEach((FieldError error) -> {
                response.addMessage(error.getField(),
                        messageSource.getMessage(error.getCode(),
                                null, LocaleContextHolder.getLocale()));
            });

            response.setCode("204");

            return response;
        } else {
            response.setCode("200");

            ProjectAttachment attachment = new ProjectAttachment();

            attachment.setAttachmentScope(attachmentDto.getFile().getOriginalFilename());
            attachment.setProject(projectService.getById(attachmentDto.getProjectId()));
            attachment.setName(attachmentDto.getName());
            attachment.setAttachment(attachmentDto.getFile().getBytes());
            Long id = attachmentService.createProjectAttachment(attachment);
            
            response.addMessage("id", String.valueOf(id));
            response.addMessage("name", attachment.getName());
            return response;
        }
    }

    @RequestMapping(value = "/projectAttachment/{attachmentId}", method = RequestMethod.GET)
    @ResponseBody
    public String downloadDocument(HttpServletResponse response, @PathVariable("attachmentId") Long attachmentId)
            throws IOException {
        ProjectAttachment document = attachmentService.getById(attachmentId);

        response.setContentLength(document.getAttachment().length);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + document.getAttachmentScope() + "\"");

        FileCopyUtils.copy(document.getAttachment(), response.getOutputStream());
        return "";
    }

    @RequestMapping(value = "/removeProjectAttachment", method = RequestMethod.POST)
    @ResponseBody
    public void removeProjectAttachment(@RequestParam("id_attachment") Long id) {
        attachmentService.deleteProjectAttachment(attachmentService.getById(id));
    }


    @RequestMapping(value = "/getAvailableCriteria", method = RequestMethod.GET)
    @ResponseBody
    public List<CriterionDto> getAvailableCriteria(@RequestParam Long projectId) {
        List<CriterionDto> criterionDtos = new ArrayList<>();
        for (Criterion criterion : criterionService.getProjectUnusedCriteria(projectId)) {
            criterionDtos.add(new CriterionDto(criterion));
        }
        return criterionDtos;
    }

    @RequestMapping(value = "/addCriteria", method = RequestMethod.POST)
    @ResponseBody
    public CriterionDto addCriteria(@RequestParam Long projectId, @RequestParam String criteriaTitle) {
        Criterion criterion = criterionService.getByName(criteriaTitle);
        projectService.addCriteria(projectId, criterion);
        return new CriterionDto(criterion.getId(), criterion.getTitle(), criterionService.isRatedInProject(projectId, criterion));
    }

    @RequestMapping(value = "/deleteProjectCriteria", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity deleteProjectCriteria(@RequestParam Long projectId, @RequestParam String criteriaTitle) throws CriteriaDeleteException {
        if (criterionService.isRatedInProject(projectId, criterionService.getByName(criteriaTitle)))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This criteria has meeting result and cannot be deleted");
        if (finalReviewCriterionService.isExists(criterionService.getByName(criteriaTitle), projectId))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This criteria has final review and cannot be deleted");
        projectService.deleteProjectCriterion(projectId, criterionService.getByName(criteriaTitle));
        return ResponseEntity.ok().body("Success");
    }

    @RequestMapping(value = "/getCriteriaAndGroups", method = RequestMethod.GET)
    @ResponseBody
    public ProjectsCriteriaGroupsDto getCriteriaAndGroups(@RequestParam Long projectId) {
        return new ProjectsCriteriaGroupsDto(groupService.getByProjectId(projectId), criterionService.getByProject(projectId));
    }

    @RequestMapping(value = "/saveMeeting", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity saveMeeting(@RequestBody AddCriteriaDto dto) {
        if(!meetingValidator.checkMeetingName(dto.getName()) && !meetingValidator.checkMeetingPlace(dto.getPlace()) && !meetingValidator.checkMeetingDate(dto.getDate())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation failed");
        }
        if (meetingService.addMeetings(dto) == 0)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Meetings with this date already created.");
        return ResponseEntity.ok().body("Success");
    }

}