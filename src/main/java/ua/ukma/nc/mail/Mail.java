
package ua.ukma.nc.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

/**
 * @author Oleh Khomandiak
 */
@Component
public class Mail {

	@Autowired
	private JavaMailSenderImpl mailSender;
	
		
	Mail(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void sendMail(String from, String to, String subject, String msg) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		try {
			mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
	
}
