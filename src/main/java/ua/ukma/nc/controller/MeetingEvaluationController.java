package ua.ukma.nc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.ukma.nc.entity.*;
import ua.ukma.nc.entity.impl.real.MeetingReviewImpl;
import ua.ukma.nc.service.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Alex_Frankiv on 14.11.2016.
 */
@Controller
@RequestMapping (value = "/meeting")
public class MeetingEvaluationController {

    @Autowired
    MeetingService meetingService;

    @Autowired
    StudentService studentService;

    @Autowired
    UserService userService;

    @Autowired
    MeetingReviewService meetingReviewService;

    @Autowired
    MeetingResultService meetingResultService;

    @Autowired
    CriterionService criterionService;

    @RequestMapping (value = "/set_no_attendance", method = RequestMethod.POST)
    public @ResponseBody String setAttendance(@RequestParam Long studentId, @RequestParam Long meetingId){
        MeetingReview review = new MeetingReviewImpl();
        User mentor = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        review.setMentor(mentor);
        review.setMeeting(meetingService.getById(meetingId));
        review.setStudent(userService.getById(studentId));
        review.setType("A");
        meetingReviewService.createMeetingReview(review);
        return "true";
    }

    @RequestMapping (value = "/evaluate", method = RequestMethod.GET)
    public ModelAndView evaluate(@RequestParam Long studentId, @RequestParam Long meetingId){
        ModelAndView mv = new ModelAndView("evaluateStudent");
        MeetingReview review = meetingReviewService.getByMeetingStudent(meetingId, studentId);
        User student = userService.getById(studentId);
        Meeting meeting = meetingService.getById(meetingId);
        List<MeetingResult> results;
        if(review==null){
            User mentor = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            review = new MeetingReviewImpl();
            review.setMentor(mentor);
            review.setMeeting(meeting);
            review.setStudent(student);
            review.setType("L");
            meetingReviewService.createMeetingReview(review);
            results = new LinkedList<MeetingResult>();
            for(Criterion criterion :criterionService.getByMeeting(meetingId)){
                MeetingResult mr = new MeetingResult();
//                mr.setMeetingReview(review);
                mr.setCriterion(criterion);
                results.add(mr);
            }
        }
        else {
            results = meetingResultService.getByReview(review.getId());
            //TO-DO must be resolved!!!
            for(MeetingResult res : results) {
                res.setCriterion(criterionService.getById(res.getCriterion().getId()));
            }
        }
        mv.addObject("results", results);
        mv.addObject("meeting", meeting);
        mv.addObject("student", student);
        mv.addObject("review", review);
        mv.addObject("title", "Meeting Evaluation");
        return mv;
    }

//    @RequestMapping (value = "/do_evaluate", method = RequestMethod.POST)
//    public @ResponseBody String doEvaluate(@ModelAttribute MeetingResult[] results, @RequestParam Long reviewId, @RequestParam String reviewComment){
//        for(MeetingResult result : results)
//            System.out.println(result);
//        System.out.println(reviewId);
//        System.out.println(reviewComment);
//        return "true";
//    }
}
