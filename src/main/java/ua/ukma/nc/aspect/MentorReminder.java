package ua.ukma.nc.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import ua.ukma.nc.dto.*;
import ua.ukma.nc.entity.Meeting;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.service.MeetingService;
import ua.ukma.nc.service.UserService;

import org.aspectj.lang.annotation.Around;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;

@Aspect
@Component
public class MentorReminder {

	@Autowired
	private MeetingService meetingService;

	@Autowired
	private UserService userService;

	@Around("execution(org.springframework.web.servlet.ModelAndView ua.ukma.nc.controller.*.*(..))")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {

		ModelAndView retVal = (ModelAndView) pjp.proceed();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		boolean isMentor = authorities.contains(new SimpleGrantedAuthority("ROLE_MENTOR"));

		if (isMentor) {
			User mentor = userService.getByEmail(authentication.getName());
			List<Meeting> meetings = meetingService.getMentorsUncheckedMeetings(mentor.getId());

			List<MeetingDto> meetingsDto = new ArrayList<MeetingDto>();

			for (Meeting meeting : meetings) {
				MeetingDto meetingDto = new MeetingDto();
				meetingDto.setName(meeting.getName());
				meetingDto.setId(meeting.getId());
				meetingDto.setTime(meeting.getTime());
				meetingDto.setPlace(meeting.getPlace());
				
				meetingsDto.add(meetingDto);
			}

			retVal.addObject("unmarkedMeetings", meetingsDto);
		}

		return retVal;
	}

}