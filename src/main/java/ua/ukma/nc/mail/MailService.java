
package ua.ukma.nc.mail;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.Project;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.service.ProjectService;
import ua.ukma.nc.service.UserService;

/**
 * @author Oleh Khomandiak
 */
@Configurable
@Service
public class MailService {

	@Autowired
	private Mail mail;

	@Autowired
	private UserService userService;

	@Autowired
	private Environment env;

	@Autowired
	private ProjectService projectService;

	public String newProject(User user, Project project) {
		String from = env.getProperty("mail.username");
		String to = user.getEmail();
		StringBuilder message = new StringBuilder();
		message.append("Hello" + user.getFirstName() + " " + user.getLastName());
		message.append(". Your project " + project.getName() + " starts " + project.getStartDate());
		message.append(". Get ready soon.");
		try {
			mail.sendMail(from, to, "New project", message.toString());
			return "Sent succesfully";
		} catch (MailException e) {
			return e.toString();
		}

	}
	
	public String newGroup(User user, Group group) {
		String from = env.getProperty("mail.username");
		String to = user.getEmail();
		StringBuilder message = new StringBuilder();
		message.append("Hello" + user.getFirstName() + " " + user.getLastName());
		message.append(". Your have been added to group " + group.getName());
		try {
			mail.sendMail(from, to, "Your group", message.toString());
			return "Sent succesfully";
		} catch (MailException e) {
			return e.toString();
		}

	}

	@Scheduled(cron = "14 28 02 * * *")
	public void projectStart() {
		List<Project> projects = projectService.getAllUpcoming();
		Calendar soon = Calendar.getInstance();
		soon.add(Calendar.DATE, 3);
		Calendar start = Calendar.getInstance();

		for (Project project : projects) {
			start.setTime(project.getStartDate());
			if (start.get(Calendar.DAY_OF_YEAR) - soon.get(Calendar.DAY_OF_YEAR) == 0) {
				List<User> users = userService.studentsByProjectId(project.getId());
				for (User user : users)
					newProject(user, project);
			}

		}
	}

}
