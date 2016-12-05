package ua.ukma.nc.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author Oleh Khomandiak
 */
@Configuration
@Configurable
@ComponentScan(basePackages = "ua.ukma.nc")
@PropertySource("classpath:mail.properties")
public class MailConfig {

	@Autowired
	Environment env;

	@Bean
	public JavaMailSenderImpl javaMailService() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		applyProperties(javaMailSender);
		return javaMailSender;
	}

	private void applyProperties(JavaMailSenderImpl sender) {
		sender.setHost(env.getProperty("mail.host"));
		sender.setPort(Integer.parseInt(env.getProperty("mail.port")));
		sender.setUsername(env.getProperty("mail.username"));
		sender.setPassword(env.getProperty("mail.password"));
		sender.setJavaMailProperties(asProperties());
	}

	private Properties asProperties() {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.starttls.enable", "true");
		return properties;
	}

}