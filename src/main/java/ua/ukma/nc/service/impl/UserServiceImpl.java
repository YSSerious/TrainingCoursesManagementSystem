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
import ua.ukma.nc.entity.Role;
import ua.ukma.nc.entity.Status;
import ua.ukma.nc.entity.StatusLog;
import ua.ukma.nc.entity.StudentStatus;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.exception.StatusSwitchException;
import ua.ukma.nc.service.FinalReviewService;
import ua.ukma.nc.service.GroupService;
import ua.ukma.nc.service.StatusLogService;
import ua.ukma.nc.service.StatusService;
import ua.ukma.nc.service.StudentStatusService;
import ua.ukma.nc.service.UserService;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

/**
 * Created by Алексей on 21.10.2016.
 */
@Service
public class UserServiceImpl implements UserService {

	private static final Long INACTIVE_STATUS = 1l;

	private static final Long ROLE_STUDENT = 4L;
	private static final Long ROLE_MENTOR = 2L;
	private static final Long ROLE_HR = 3L;
	private static final Long ROLE_ADMIN = 1L;

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

		if (role.getId() == ROLE_STUDENT)
			if (!studentStatusService.exists(user.getId())) {
				StudentStatus studentStatus = new StudentStatus();
				studentStatus.setStudent(user);
				studentStatus.setStatus(statusService.getById(INACTIVE_STATUS));

				studentStatusService.createStudentStatus(studentStatus);

			}

		return userDao.addRole(user, role);
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

		Long groupId = null;
		
		if(statusLogService.exists(id))
			groupId = statusLogService.getNewestGroup(id);
		else
			throw new StatusSwitchException(id, "Student has no projects!");
		
		if (oldStatus == 0){
			throw new StatusSwitchException(id, "Wrong user was selected!");
		}else if (oldStatus == 1 && statusId == 3 && isHR) {
			

			if (!finalReviewService.exists(id, groupId, "F"))
				throw new StatusSwitchException(id, "Student hasn't final review!");
			else
				changeStatus(id, statusId, oldStatus, name, commentary);

		} else if (oldStatus == 2 && statusId == 1 && (isHR || isMentor)) {
			changeStatus(id, statusId, oldStatus, name, commentary);

		} else if (oldStatus == 2 && statusId == 3 && isHR) {

			if (!finalReviewService.exists(id, groupId, "F"))
				throw new StatusSwitchException(id, "Student hasn't final review!");
			else
				changeStatus(id, statusId, oldStatus, name, commentary);

		} else if (oldStatus == 3 && statusId == 4 && isHR) {
			if (!finalReviewService.exists(id, groupId, "T") || !finalReviewService.exists(id, groupId, "G"))
				throw new StatusSwitchException(id, "Student hasn't general or technical review!");
			else
				changeStatus(id, statusId, oldStatus, name, commentary);
		} else if (oldStatus == 3 && statusId == 1 && isHR) {
			if (!finalReviewService.exists(id, groupId, "T") || !finalReviewService.exists(id, groupId, "G"))
				throw new StatusSwitchException(id, "Student hasn't general or technical review!");
			else
				changeStatus(id, statusId, oldStatus, name, commentary);
		} else
			throw new StatusSwitchException(id, "Incorrect statuses!");
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

	@Override
	public boolean canView(Long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		boolean showAllUsers = authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
				|| authorities.contains(new SimpleGrantedAuthority("ROLE_HR"));

		if (!showAllUsers) {
			String name = authentication.getName();
			return userDao.canView(name, id);
		}

		return true;
	}

	@Override
	public void deleteRole(User user, Long roleId) {

		if (roleId == ROLE_STUDENT) {
			if (hasStudentProjects(user.getId()))
				throw new IllegalArgumentException();
			else if (studentStatusService.exists(user.getId())) {
				StudentStatus studentStatus = new StudentStatus();
				studentStatus.setStudent(user);
				studentStatusService.deleteStudentStatus(studentStatus);
			}
		} else if (roleId == ROLE_MENTOR) {
			if (hasMentorProjects(user.getId()))
				throw new IllegalArgumentException();
		} else if (roleId == ROLE_HR) {
			if (hasHRReviews(user.getId()))
				throw new IllegalArgumentException();
		} else if (roleId == ROLE_ADMIN) {
			if (isCurrentUser(user.getId()))
				throw new IllegalArgumentException();
		}
		userDao.deleteRole(user, roleId);
	}

	@Override
	public boolean hasReview(Long studentId, Long groupId) {
		return userDao.hasReviews(studentId, groupId);
	}

	@Override
	public List<User> getByName(String name, int limit, int offset) {
		return userDao.getByName(name, limit, offset);
	}

	@Override
	public List<User> getByName(String name, String secondName, String lastName, int limit, int offset) {
		return userDao.getByName(name, secondName, lastName, limit, offset);
	}

	@Override
	public List<User> getByName(String value1, String value2, int limit, int offset) {
		return userDao.getByName(value1, value2, limit, offset);
	}

	@Override
	public List<User> studentsByProjectId(Long projectId) {
		return userDao.studentsByProjectId(projectId);
	}

	@Override
	public List<User> studentsByGroupId(Long groupId) {
		return userDao.studentsByGroupId(groupId);
	}

	@Override
	public List<User> studentsByProjectName(String name, int limit, int offset) {
		return userDao.studentsByProjectName(name, limit, offset);
	}

	@Override
	public List<User> studentsByGroupName(String name, int limit, int offset) {
		return userDao.studentsByGroupName(name, limit, offset);
	}

	@Override
	public int countGroup(String name) {
		return userDao.countGroup(name);
	}

	@Override
	public int countProject(String name) {
		return userDao.countProject(name);
	}

	@Override
	public int countName(String name) {
		return userDao.countName(name);
	}

	@Override
	public int countHalfName(String value1, String value2) {
		return userDao.countHalfName(value1, value2);
	}

	@Override
	public int countFullName(String name, String secondName, String lastName) {
		return userDao.countFullName(name, secondName, lastName);
	}

	@Override
	public boolean hasStudentProjects(Long studentId) {
		return userDao.hasStudentProjects(studentId);
	}

	@Override
	public boolean hasMentorProjects(Long mentorId) {
		return userDao.hasMentorProjects(mentorId);
	}

	@Override
	public boolean hasHRReviews(Long hrId) {
		return userDao.hasHRReviews(hrId);
	}

	@Override
	public boolean isUsingRole(Long userId, Long roleId) {
		if (roleId == ROLE_STUDENT)
			return hasStudentProjects(userId);
		else if (roleId == ROLE_MENTOR)
			return hasMentorProjects(userId);
		else if (roleId == ROLE_HR)
			return hasHRReviews(userId);
		else if (roleId == ROLE_ADMIN)
			return isCurrentUser(userId);
		return true;
	}

	@Override
	public boolean isCurrentUser(Long userId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		User user = getByEmail(email);
		return userId == user.getId();

	}
}
