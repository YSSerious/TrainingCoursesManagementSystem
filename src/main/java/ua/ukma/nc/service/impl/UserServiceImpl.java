package ua.ukma.nc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.ukma.nc.dao.UserDao;
import ua.ukma.nc.entity.FinalReview;
import ua.ukma.nc.entity.Meeting;
import ua.ukma.nc.entity.MeetingReview;
import ua.ukma.nc.entity.Role;
import ua.ukma.nc.entity.Status;
import ua.ukma.nc.entity.StatusLog;
import ua.ukma.nc.entity.StudentStatus;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.entity.impl.real.FinalReviewImpl;
import ua.ukma.nc.entity.impl.real.MeetingReviewImpl;
import ua.ukma.nc.entity.impl.real.StatusImpl;
import ua.ukma.nc.query.clause.project.MentorLogicClause;
import ua.ukma.nc.service.FinalReviewService;
import ua.ukma.nc.service.GroupService;
import ua.ukma.nc.service.MeetingReviewService;
import ua.ukma.nc.service.MeetingService;
import ua.ukma.nc.service.StatusLogService;
import ua.ukma.nc.service.StatusService;
import ua.ukma.nc.service.StudentStatusService;
import ua.ukma.nc.service.UserService;

import javax.sql.DataSource;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

/**
 * Created by Алексей on 21.10.2016.
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private MeetingReviewService meetingReviewService;

	@Autowired
	private UserDao userDao;

	@Autowired
	private StatusService statusService;

	@Autowired
	private StatusLogService statusLogService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private StudentStatusService studentStatusService;

	@Autowired
	private MeetingService meetingService;

	@Autowired
	private FinalReviewService finalReviewService;

	@Override
	public User getByEmail(String email) {
		return userDao.getByEmail(email);
	}

	@Override
	public User getById(Long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		boolean showAllUsers = authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
				|| authorities.contains(new SimpleGrantedAuthority("ROLE_HR"));

		if (!showAllUsers) {
			String name = authentication.getName();

			if (userDao.canView(name, id))
				return userDao.getById(id);

			return userDao.getByEmail(name);
		}

		return userDao.getById(id);
	}

	@Override
	public int count() {
		return userDao.count();
	}

	@Override
	public boolean isExist(User user) {
		return userDao.isExist(user);
	}

	@Override
	public int addRole(User user, Role role) {
		return 0;
	}

	@Override
	public int setStatus(User user, Status status) {
		return 0;
	}

	@Override
	public int deleteUser(User user) {
		return userDao.deleteUser(user);
	}

	@Override
	public int updateUser(User user) {
		return userDao.updateUser(user);
	}

	@Override
	public List<User> getAll() {
		return userDao.getAll();
	}

	@Override
	public List<User> getSome(int limit, int offset) {
		return userDao.getSome(limit, offset);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void createUser(User user, List<Role> roles) {
		userDao.createUser(user);
		User createdUser = userDao.getByEmail(user.getEmail());
		boolean isStudent = false;
		for (Role role : roles) {
			userDao.addRole(createdUser, role);
			if (role.getTitle().equalsIgnoreCase("ROLE_STUDENT"))
				isStudent = true;
		}
		if (isStudent)
			userDao.setStatus(createdUser, statusService.getById(1L));
	}

	@Override
	public void changeStatus(Long id, Long statusId, String commentary) {
		Long oldStatus = userDao.getById(id).getStudentStatus().getStatus().getId();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		boolean isMentor = authorities.contains(new SimpleGrantedAuthority("ROLE_MENTOR"));
		boolean isHR = authorities.contains(new SimpleGrantedAuthority("ROLE_HR"));
		String name = authentication.getName();

		if (oldStatus == 0)
			throw new IllegalArgumentException("Wrong student was selected!");
		else if (oldStatus == 1 && statusId == 3 && isHR) {
			Long groupId = statusLogService.getNewestGroup(id);

			if (!finalReviewService.exists(id, groupId, "M"))
				throw new IllegalArgumentException("Student hasn't final review!");
			else
				changeStatus(id, statusId, oldStatus, name, commentary);
			
		} else if (oldStatus == 2 && statusId == 1 && (isHR || isMentor)) {
			changeStatus(id, statusId, oldStatus, name, commentary);

			Long groupId = statusLogService.getNewestGroup(id);
			List<Meeting> unmarked = meetingService.getWithoutReview(groupId, id);
			User employee = userDao.getByEmail(name);
			User student = userDao.getById(id);

			for (Meeting meeting : unmarked) {
				MeetingReview meetingReview = new MeetingReviewImpl();
				meetingReview.setCommentary("Gone...");
				meetingReview.setMeeting(meeting);
				meetingReview.setMentor(employee);
				meetingReview.setStudent(student);
				meetingReview.setType("L");

				meetingReviewService.createMeetingReview(meetingReview);
			}

			if (!finalReviewService.exists(id, groupId, "M")) {
				FinalReview finalReview = new FinalReviewImpl();
				finalReview.setCommentary("Gone...");
				finalReview.setDate(new Timestamp(System.currentTimeMillis()));
				finalReview.setEmployee(employee);
				finalReview.setStudent(student);
				finalReview.setType("L");
				finalReview.setProject(groupService.getById(groupId).getProject());
			}

		} else if (oldStatus == 2 && statusId == 3 && isHR) {
			Long groupId = statusLogService.getNewestGroup(id);

			if (!finalReviewService.exists(id, groupId, "M"))
				throw new IllegalArgumentException("Student hasn't final review!");
			else
				changeStatus(id, statusId, oldStatus, name, commentary);

		} else if (oldStatus == 3 && statusId == 4 && isHR) {
			Long groupId = statusLogService.getNewestGroup(id);

			if (!finalReviewService.exists(id, groupId, "T") || !finalReviewService.exists(id, groupId, "G"))
				throw new IllegalArgumentException("Student hasn't final review!");
			else
				changeStatus(id, statusId, oldStatus, name, commentary);
		} else if (oldStatus == 3 && statusId == 1 && isHR) {
			Long groupId = statusLogService.getNewestGroup(id);

			if (!finalReviewService.exists(id, groupId, "T") || !finalReviewService.exists(id, groupId, "G"))
				throw new IllegalArgumentException("Student hasn't final review!");
			else
				changeStatus(id, statusId, oldStatus, name, commentary);
		} else
			throw new IllegalArgumentException("Incorrect data!");
	}

	private void changeStatus(Long id, Long statusId, Long oldStatus, String name, String commentary) {
		Long idGroup = statusLogService.getNewestGroup(id);
		StatusLog statusLog = new StatusLog();
		User student = userDao.getById(id);
		Status status = statusService.getById(statusId);

		statusLog.setDate(new Timestamp(System.currentTimeMillis()));
		statusLog.setOldStatus(statusService.getById(oldStatus));
		statusLog.setNewStatus(status);
		statusLog.setGroup(groupService.getById(idGroup));
		statusLog.setEmployee(userDao.getByEmail(name));
		statusLog.setStudent(student);
		statusLog.setCommentary(commentary);

		statusLogService.createStatusLog(statusLog);
		StudentStatus studentStatus = new StudentStatus();
		studentStatus.setStudent(student);
		studentStatus.setStatus(status);

		studentStatusService.updateStudentStatus(studentStatus);
	}
}
