
package ua.ukma.nc.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author Oleh Khomandiak on 7 лист. 2016 р.
 */
@Configuration
@Configurable
@ComponentScan(basePackages = "ua.ukma.nc")
public class MailConfig {

	private String host = "smtp.gmail.com";

	private int port = 587;

	private String username = "devcor2015@gmail.com";

	private String password = "devcorMade2016";

	@Bean
	public JavaMailSenderImpl javaMailService() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		applyProperties(javaMailSender);
		System.out.println("check");
		return javaMailSender;
	}

	private void applyProperties(JavaMailSenderImpl sender) {
		sender.setHost(host);
		sender.setPort(port);
		sender.setUsername(username);
		sender.setPassword(password);
		sender.setJavaMailProperties(asProperties());
	}

	private Properties asProperties() {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.starttls.enable", "true");
		return properties;
	}

}