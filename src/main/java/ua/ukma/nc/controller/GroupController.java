
package ua.ukma.nc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ua.ukma.nc.dto.*;
import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.GroupAttachment;
import ua.ukma.nc.entity.Meeting;
import ua.ukma.nc.entity.MeetingReview;
import ua.ukma.nc.entity.Project;
import ua.ukma.nc.entity.StudentStatus;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.entity.impl.real.GroupImpl;
import ua.ukma.nc.entity.impl.real.ProjectImpl;
import ua.ukma.nc.service.CategoryService;
import ua.ukma.nc.service.CriterionService;
import ua.ukma.nc.service.GroupAttachmentService;
import ua.ukma.nc.service.GroupService;
import ua.ukma.nc.service.MeetingReviewService;
import ua.ukma.nc.service.MeetingService;
import ua.ukma.nc.service.StudentStatusService;
import ua.ukma.nc.service.UserService;
import ua.ukma.nc.util.exception.MeetingDeleteException;
import ua.ukma.nc.util.exception.RemoveStudentFromGroupException;
import ua.ukma.nc.validator.GroupDeleteValidator;
import ua.ukma.nc.validator.GroupAttachmentFormValidator;
import ua.ukma.nc.validator.GroupEditValidator;
import ua.ukma.nc.validator.GroupFormValidator;
import ua.ukma.nc.vo.AjaxResponse;

/**
 * Created by Nastasia on 05.11.2016.
 */
@Controller
@RequestMapping("/groups")
public class GroupController {
    @Autowired
    private GroupAttachmentService groupAttachmentService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CriterionService criterionService;

    @Autowired
    private StudentStatusService studentStatusService;

    @Autowired
    private MeetingReviewService meetingReviewService;

    @Autowired
    private GroupFormValidator groupFormValidator;

    @Autowired
    private GroupEditValidator groupEditValidator;
    
    @Autowired
    private GroupDeleteValidator groupDeleteValidator;
    
    @Autowired
    private MessageSource messageSource;

    private static Logger log = LoggerFactory.getLogger(HomeController.class.getName());

	@Autowired
    private GroupAttachmentFormValidator groupAttachmentFormValidator;
     
    @InitBinder("groupAttachmentForm")
    protected void initBinderFileBucket(WebDataBinder binder) {
       binder.setValidator(groupAttachmentFormValidator);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse addGroup(@RequestBody GroupDto groupDto) {
		log.info("GROUP: " + groupDto.toString());
		DataBinder dataBinder = new WebDataBinder(groupDto);
		dataBinder.setValidator(groupFormValidator);
		dataBinder.validate();
		BindingResult result = dataBinder.getBindingResult();
		AjaxResponse response = new AjaxResponse();
		if (!result.hasErrors()) {
			Project project = new ProjectImpl();
			project.setId(groupDto.getProjectId());
			Group group = new GroupImpl();
			group.setProject(project);
			group.setName(groupDto.getName());
			groupService.createGroup(group);
			response.setCode("200");
			response.addMessage("groupId", Long.toString(groupService.getByName(groupDto.getName()).getId()));
		} else {
			response.setCode("204");
			result.getFieldErrors().stream().forEach((FieldError error) -> {
				response.addMessage(error.getField(),
					messageSource.getMessage(error.getCode(), null, LocaleContextHolder.getLocale()));
			});
		}
		return response;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse editGroup(@RequestBody GroupDto groupDto) {
		DataBinder dataBinder = new WebDataBinder(groupDto);
		dataBinder.setValidator(groupEditValidator);
		dataBinder.validate();
		BindingResult result = dataBinder.getBindingResult();
		AjaxResponse response = new AjaxResponse();
		if (!result.hasErrors()) {
			Project project = new ProjectImpl();
			project.setId(groupDto.getProjectId());
			Group group = new GroupImpl();
			group.setId(groupDto.getId());
			group.setProject(project);
			group.setName(groupDto.getName());
			groupService.updateGroup(group);
			response.setCode("200");
			response.addMessage("groupId", Long.toString(groupService.getByName(groupDto.getName()).getId()));
		} else {
			response.setCode("204");
			result.getFieldErrors().stream().forEach((FieldError error) -> {
				response.addMessage(error.getField(),
					messageSource.getMessage(error.getCode(), null, LocaleContextHolder.getLocale()));
			});
			result.getGlobalErrors().stream().forEach((ObjectError error) -> {
				response.addMessage("general", messageSource.getMessage(error.getCode(),
					null, LocaleContextHolder.getLocale()));
			});
		}
		return response;
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public AjaxResponse deleteGroup(@RequestParam Long groupId) {
		GroupDto groupDto = new GroupDto(groupId);
		DataBinder dataBinder = new WebDataBinder(groupDto);
		dataBinder.setValidator(groupDeleteValidator);
		dataBinder.validate();
		BindingResult result = dataBinder.getBindingResult();
		AjaxResponse response = new AjaxResponse();
		if (!result.hasErrors()) {
			Group group = groupService.getById(groupId);
			groupService.deleteGroup(group);
			response.setCode("200");
		} else {
			response.setCode("204");
			result.getFieldErrors().stream().forEach((FieldError error) -> {
				response.addMessage(error.getField(),
					messageSource.getMessage(error.getCode(),
						null, LocaleContextHolder.getLocale()));
			});
			result.getGlobalErrors().stream().forEach((ObjectError error) -> {
				response.addMessage("general", messageSource.getMessage(error.getCode(),
					null, LocaleContextHolder.getLocale()));
			});
		}
		return response;
	}

    @RequestMapping(value = "/group", method = RequestMethod.GET)
    public ModelAndView getGroup(@RequestParam Long id) {

        ModelAndView model = new ModelAndView();
        GroupDto group = new GroupDto(groupService.getById(id));

        model.addObject("groupAttachmentForm", new GroupAttachmentFormDto());
        List<CategoryDto> categories = categoryService.getByProjectId(group.getProject().getId()).stream()
                .map(CategoryDto::new).collect(Collectors.toList());

        List<CriterionDto> criteria = criterionService.getByProject(group.getProject().getId()).stream()
                .map(CriterionDto::new).collect(Collectors.toList());

        List<UserDto> selectStudents = userService.studentsByGroupId(id).stream().map(UserDto::new)
                .collect(Collectors.toList());

        model.addObject("categories", categories);
        model.addObject("criteria", criteria);
        model.addObject("selectStudents", selectStudents);

        List<User> students = groupService.getStudents(id);

        List<StudentStatus> studentsWithStatus = new ArrayList<StudentStatus>();
        for (User us : students) {
            studentsWithStatus.add(studentStatusService.getByUserId(us.getId()));
        }

        Map<StudentStatus, List<MeetingReview>> studentAndReviews = new HashMap<>();
        for (StudentStatus us : studentsWithStatus) {
            List<MeetingReview> list = new ArrayList<>();
            list.addAll(
                    (meetingReviewService.getByProjectStudent(group.getProject().getId(), us.getStudent().getId())));
            studentAndReviews.put(us, list);
        }
        // studentsWithStatus.get(0).getStatus().
        List<User> mentors = groupService.getMentors(id);

        List<GroupAttachment> groupAttachments = groupAttachmentService.getByGroup(id);

        List<MeetingDto> meetingDtos = new ArrayList<>();
        for (Meeting meeting : meetingService.getByGroup(id)) {
            meetingDtos.add(new MeetingDto(meeting.getId(), meeting.getName(), meeting.getTime(), meeting.getPlace(),
                    meetingService.isReviewed(meeting.getId())));
        }

        String projectName = group.getProject().getName();
        model.addObject("group", group);
        model.addObject("projectName", projectName);

        model.addObject("students", studentAndReviews);
        model.addObject("mentors", mentors);
        model.addObject("meetings", meetingDtos);
        model.addObject("group-id", group.getId());
        model.addObject("attachments", groupAttachments);
        model.addObject("groupId", group.getId());
        model.addObject("attendance", groupService.getAttendaceTable(id));

        model.setViewName("group-view");

        log.info("Getting group with name : " + group.getName() + " and project: " + group.getProject().getName());
        log.info("Group information sent");
        return model;
    }

    @RequestMapping(value = "/addAttachment", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse addGroupAttachment(@Valid @ModelAttribute("groupAttachmentForm") GroupAttachmentFormDto attachmentDto,
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

            GroupAttachment attachment = new GroupAttachment();

            attachment.setAttachmentScope(attachmentDto.getFile().getOriginalFilename());
            attachment.setGroup(groupService.getById(attachmentDto.getGroupId()));
            attachment.setName(attachmentDto.getName());
            attachment.setAttachment(attachmentDto.getFile().getBytes());
            Long id = groupAttachmentService.createGroupAttachment(attachment);

            response.addMessage("id", String.valueOf(id));
            response.addMessage("name", attachment.getName());
            return response;
        }
    }

    @RequestMapping(value = "/groupAttachment/{attachmentId}", method = RequestMethod.GET)
    @ResponseBody
    public String downloadDocument(HttpServletResponse response, @PathVariable("attachmentId") Long attachmentId)
            throws IOException {
        GroupAttachment document = groupAttachmentService.getById(attachmentId);

        response.setContentLength(document.getAttachment().length);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + document.getAttachmentScope() + "\"");

        FileCopyUtils.copy(document.getAttachment(), response.getOutputStream());
        return "";
    }

    @RequestMapping(value = "/removeMentor", method = RequestMethod.POST)
    @ResponseBody
    public String removeMentor(@RequestParam Long groupId, @RequestParam Long userId) {
        groupService.removeMentor(groupId, userId);
        return "Deleted successfully";
    }

    @RequestMapping(value = "/removeStudent", method = RequestMethod.POST)
    @ResponseBody
    public String removeStudent(@RequestParam Long groupId, @RequestParam Long userId)
            throws RemoveStudentFromGroupException {
        if (!userService.hasReview(userId, groupId)) {
            throw new RemoveStudentFromGroupException("Student has reviews, removing is forbidden");
        } else {
            groupService.removeStudent(groupId, userId);
            return "Deleted successfully";
        }
    }

    @RequestMapping(value = "/deleteAttachment", method = RequestMethod.POST)
    @ResponseBody
    public void deleteGroupAttachment(@RequestParam("id") Long idAttachment) {
        GroupAttachment attachment = groupAttachmentService.getById(idAttachment);
        groupAttachmentService.deleteGroupAttachment(attachment);

    }

    @RequestMapping(value = "/editMeeting", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity editMeeting(@RequestParam Long id, @RequestParam String name, @RequestParam String date,
                                  @RequestParam String place) {
        int check = meetingService.editMeeting(id, name, date, place);
        if (check == 1) {
            Meeting meeting = meetingService.getById(id);
            return ResponseEntity.ok().body(new MeetingDto(meeting.getId(), meeting.getName(), meeting.getTime(), meeting.getPlace()));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Meeting was not edited.");
    }

    @RequestMapping(value = "/deleteMeeting", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity deleteMeeting(@RequestParam Long meetingId) throws MeetingDeleteException {
        if (meetingService.isReviewed(meetingId))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This meeting was reviewed and cannot be deleted.");
        meetingService.deleteMeeting(meetingService.getById(meetingId));
        return ResponseEntity.ok().body("Success");
    }

}
