package ua.ukma.nc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessagesConfig {
	
	@Bean(name="messageSource")
	public ResourceBundleMessageSource messageSource(){
    	ResourceBundleMessageSource source = new ResourceBundleMessageSource();
    	source.setBasenames("validation", "messages");
		source.setDefaultEncoding("UTF-8");
    	return source;
    }
}