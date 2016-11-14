package ua.ukma.nc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Alex_Frankiv on 14.11.2016.
 */
@Controller
@RequestMapping (value = "/meeting")
public class MeetingEvaluationController {

    @RequestMapping (value = "/set_attendance", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String setAttendance(){

        return "true";
    }

    @RequestMapping (value = "/evaluate", method = RequestMethod.GET)
    public ModelAndView evaluate(){
        ModelAndView mv = new ModelAndView("evaluateStudent");

        return mv;
    }
}
