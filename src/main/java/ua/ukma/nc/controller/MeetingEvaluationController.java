package ua.ukma.nc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.ukma.nc.entity.Meeting;
import ua.ukma.nc.entity.MeetingReview;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.entity.impl.real.MeetingReviewImpl;
import ua.ukma.nc.service.MeetingReviewService;
import ua.ukma.nc.service.MeetingService;
import ua.ukma.nc.service.StudentService;
import ua.ukma.nc.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
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
    public ModelAndView evaluate(){
        ModelAndView mv = new ModelAndView("evaluateStudent");

        return mv;
    }
}
