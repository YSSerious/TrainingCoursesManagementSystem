package ua.ukma.nc.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;





/**
 * @author Oleh Khomandiak on 2 лист. 2016 р.
 *
 */
@Service
public class EmailSender {
	private static final String SENDER = "devcor2015@gmail.com";
	@Autowired	
	private  Mail mail;
	
	public void send(String receiver, String subject, String body){
		mail.sendMail(SENDER, receiver, subject, body);
	}
	


}


