
package ua.ukma.nc.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import ua.ukma.nc.entity.User;
import ua.ukma.nc.service.UserService;

/**
 *@author Oleh Khomandiak on 10 лист. 2016 р.
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
	
	
	public String registration(User user){
		String from = env.getProperty("mail.username");
		String to = user.getEmail();
		StringBuilder message = new StringBuilder();
		message.append("Hello"+user.getFirstName()+" "+user.getLastName());
		try{
		mail.sendMail(from, to, "Succesful registration", message.toString());
		return "Sent succesfully";
		}
		catch(MailException e){
		return e.toString();
		}
	}
	
	
}
