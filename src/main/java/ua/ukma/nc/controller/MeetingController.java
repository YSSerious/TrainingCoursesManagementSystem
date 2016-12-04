
package ua.ukma.nc.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ua.ukma.nc.dto.CriterionDto;
import ua.ukma.nc.dto.JsonWrapperAbsent;
import ua.ukma.nc.dto.JsonWrapperReview;
import ua.ukma.nc.dto.MarkCommentDto;
import ua.ukma.nc.dto.MarkInformation;
import ua.ukma.nc.entity.Category;
import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.Meeting;
import ua.ukma.nc.entity.MeetingResult;
import ua.ukma.nc.entity.MeetingReview;
import ua.ukma.nc.entity.Role;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.entity.impl.real.MeetingImpl;
import ua.ukma.nc.entity.impl.real.MeetingReviewImpl;
import ua.ukma.nc.service.CategoryService;
import ua.ukma.nc.service.CriterionService;
import ua.ukma.nc.service.GroupService;
import ua.ukma.nc.service.MarkService;
import ua.ukma.nc.service.MeetingResultService;
import ua.ukma.nc.service.MeetingReviewService;
import ua.ukma.nc.service.MeetingService;
import ua.ukma.nc.service.UserService;
import ua.ukma.nc.util.exception.CriteriaDeleteException;

/**
 * @author Oleh Khomandiak
 */
@Controller
public class MeetingController {

	@Autowired
	private MeetingService meetingService;

	@Autowired
	private MeetingReviewService meetingReviewService;

	@Autowired
	private MeetingResultService meetingResultService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private CriterionService criterionService;

	@Autowired
	private UserService userService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private MarkService markService;

	@RequestMapping(value = "/meeting/{id}", method = RequestMethod.GET)
	public String getMeetings(Model model, Principal principal, @PathVariable long id) {

		Meeting meeting = meetingService.getById(id);
		List<User> all = (groupService.getById(meeting.getGroup().getId())).getUsers();
		List<MeetingReview> meetingReviews = meetingReviewService.getByMeeting(id);

		List<Long> evaluated = new ArrayList<>();
		for (MeetingReview mr : meetingReviews)
			evaluated.add(mr.getStudent().getId());

		List<User> unevaluated = new ArrayList<>();
		for (User user : (all))
			for (Role role : user.getRoles())
				if (role.getId() == 4)
					if (!evaluated.contains(user.getId()))
						unevaluated.add(user);

		Map<User, List<MarkInformation>> markInformation = new HashMap<>();
		for (Long user : evaluated)
			markInformation.put(userService.getById(user), meetingResultService.getByMeeting(user, id));

		// Criteria set
		List<Criterion> criteria = criterionService.getByMeeting(id);
		List<CriterionDto> criterionDtos = new ArrayList<>();
		for (Criterion criterion : criteria) {
			criterionDtos.add(new CriterionDto(criterion.getId(), criterion.getTitle(),
					criterionService.isRatedInMeeting(id, criterion)));
		}

		List<Category> category = new ArrayList<>();
		for (Criterion criterion : criteria)
			if (!category.contains(categoryService.getById(criterion.getCategory().getId())))
				category.add(categoryService.getById(criterion.getCategory().getId()));

		Map<User, List<Criterion>> unevaluatedCriteria = new HashMap<>();
		for (User user : markInformation.keySet()) {
			List<MarkInformation> marks = markInformation.get(user);
			List<Long> cr = new ArrayList<>();
			for (MarkInformation mi : marks)
				cr.add(mi.getCriterionId());
			List<Criterion> cri = new ArrayList<>();
			for (Criterion c : criteria)
				if (!cr.contains(c.getId()))
					cri.add(c);
			unevaluatedCriteria.put(user, cri);
		}

		model.addAttribute("unevaluatedCriteria", unevaluatedCriteria);
		model.addAttribute("criteria", criterionDtos);
		model.addAttribute("marks", markInformation);
		model.addAttribute("students", unevaluated);
		model.addAttribute("meeting", meetingService.getById(id));
		model.addAttribute("categories", category);
		return "certainMeeting";
	}

	@RequestMapping(value = "/create-meeting", method = RequestMethod.GET)
	public String createMeeting(Model model, Principal principal, @RequestParam("project") long projectId) {
		Meeting meeting = new MeetingImpl();
		List<Criterion> criteria = criterionService.getByProject(projectId);
		model.addAttribute("meetingForm", meeting);
		model.addAttribute("criteria", criteria);
		model.addAttribute("url", "/create-meeting?project=" + projectId);
		return "createMeeting";
	}

	@RequestMapping(value = "/create-meeting", method = RequestMethod.POST)
	public String createMeeting(@ModelAttribute("meetingForm") @Validated MeetingImpl meeting, BindingResult result,
			final RedirectAttributes redirectAttributes, @RequestParam("project") long projectId) {
		System.out.println(meeting);
		if (!result.hasErrors()) {
			meeting.setGroup(groupService.getByProjectId(projectId).get(0));
			meetingService.createMeeting(meeting);
			redirectAttributes.addFlashAttribute("msg", "Meeting added successfully!");
			return "redirect:/projects";
		} else {
			System.out.println(result.getAllErrors());
			return "createMeeting";
		}
	}

	@RequestMapping(value = "/getAvailableMeetingCriteria", method = RequestMethod.GET)
	@ResponseBody
	public List<CriterionDto> getAvailableMeetingCriteria(@RequestParam Long meetingId) {
		List<CriterionDto> criterionDtos = new ArrayList<>();
		for (Criterion criterion : criterionService.getMeetingUnusedCriteria(meetingId,
				meetingService.getProjectByMeetingId(meetingId))) {
			criterionDtos.add(new CriterionDto(criterion));
		}
		return criterionDtos;
	}

	@RequestMapping(value = "/addMeetingCriteria", method = RequestMethod.POST)
	@ResponseBody
	public CriterionDto addMeetingCriteria(@RequestParam Long meetingId, @RequestParam String criteriaTitle) {
		Criterion criterion = criterionService.getByName(criteriaTitle);
		meetingService.addCriteria(meetingId, criterion);
		return new CriterionDto(criterion.getId(), criterion.getTitle(),
				criterionService.isRatedInProject(meetingId, criterion));
	}

	@RequestMapping(value = "/deleteMeetingCriteria", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity deleteProjectCriteria(@RequestParam Long meetingId, @RequestParam String criteriaTitle){
		Criterion criterion = criterionService.getByName(criteriaTitle);
		if (criterionService.isRatedInMeeting(meetingId, criterion))
		return ResponseEntity.status(HttpStatus.CONFLICT).body("This criteria was rated and cannot be deleted");
		meetingService.deleteMeetingCriterion(meetingId, criterion);
		return ResponseEntity.ok().body("Success");
	}

	@RequestMapping(value = "/ajax/post/evaluate/{id}", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public String postEvaluate(Principal principal, @PathVariable("id") Long userId,
			@RequestBody JsonWrapperReview data) {
		User mentor = userService.getByEmail(principal.getName());
		System.out.println(data.getData());
		MeetingReview review = null;
		System.out.println(meetingReviewService.getByMeetingStudent(data.getMeetingId(), userId) == null);
		if (meetingReviewService.getByMeetingStudent(data.getMeetingId(), userId) == null) {
			review = new MeetingReviewImpl((long) 0, "E");
			review.setStudent(userService.getById(userId));
			review.setMeeting(meetingService.getById(data.getMeetingId()));
			review.setMentor(mentor);
			review.setCommentary(data.getComment());
			System.out.println(review);
			meetingReviewService.createMeetingReview(review);
			for (MarkCommentDto value : data.getData()) {
				MeetingResult result = new MeetingResult();
				result.setId((long) 0);
				result.setCommentary(value.getCommentary());
				result.setMark(markService.getByValue(value.getValue()));
				result.setCriterion(criterionService.getById((long) value.getCriterionId()));
				result.setMeetingReview(meetingReviewService.getByMeetingStudent(data.getMeetingId(), userId));
				meetingResultService.createMeetingResult(result);
			}
		} else {
			review = meetingReviewService.getByMeetingStudent(data.getMeetingId(), userId);
			Iterator<MarkCommentDto> value = data.getData().iterator();
			Iterator<MeetingResult> result = meetingResultService.getByReview(review.getId()).iterator();
			while (value.hasNext() && result.hasNext()) {
				MeetingResult resultus = result.next();
				MarkCommentDto mark = value.next();
				resultus.setCommentary(mark.getCommentary());
				resultus.setMark(markService.getByValue(mark.getValue()));
				meetingResultService.updateMeetingResult(resultus);
			}
		}

		return "true";
	}
	
	@RequestMapping(value = "/ajax/post/absent/{id}", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public String postAbsent(Principal principal, @PathVariable("id") Long userId,
			@RequestBody JsonWrapperAbsent data) {
		
		System.out.println(userId+"  "+data.getMeetingId());
				return "true";
		
	}
	
}
