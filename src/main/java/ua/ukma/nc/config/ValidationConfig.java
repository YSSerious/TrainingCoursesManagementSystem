package ua.ukma.nc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class ValidationConfig {
	
	@Bean(name="messageSource")
	public ResourceBundleMessageSource messageSource(){
    	ResourceBundleMessageSource source = new ResourceBundleMessageSource();
    	source.setBasenames("validation");
    	return source;
    }
}