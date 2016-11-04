/**
 * 
 */
package ua.ukma.nc.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author Oleh Khomandiak on 2 лист. 2016 р.
 *
 */
@Component
public class Mail {
	@Autowired
	private JavaMailSender mailSender;  
	   
    public void setMailSender(JavaMailSender mailSender) {  
        this.mailSender = mailSender;  
    }  
   
    public void sendMail(String from, String to, String subject, String msg) {    
        SimpleMailMessage message = new SimpleMailMessage();  
        message.setFrom(from);  
        message.setTo(to);  
        message.setSubject(subject);  
        message.setText(msg);
        try{
        mailSender.send(message);     
        }
        catch(Exception e){}
    }
}
