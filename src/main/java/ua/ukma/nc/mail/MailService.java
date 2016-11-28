
package ua.ukma.nc.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import ua.ukma.nc.entity.Project;
import ua.ukma.nc.entity.User;
import ua.ukma.nc.service.UserService;

/**
 *@author Oleh Khomandiak
*/
@Configurable
@Service
public class MailService {

	@Autowired
	private Mail mail;
	
	@Autowired
	UserService userService;
	
	@Autowired
	Environment env;
	
	
	public String newProject(User user, Project project){
		String from = env.getProperty("mail.username");
		String to = user.getEmail();
		StringBuilder message = new StringBuilder();
		message.append("Hello"+user.getFirstName()+" "+user.getLastName());
		message.append(". Your project "+project.getName()+" starts "+project.getStartDate());
		message.append(". Get ready soon.");
		try{
		mail.sendMail(from, to, "New project", message.toString());
		return "Sent succesfully";
		}
		catch(MailException e){
		return e.toString();
		}
	}
	
	
}
