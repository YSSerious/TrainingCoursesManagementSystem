package ua.ukma.nc.config;

import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import ua.ukma.nc.controller.auth.SecurityUserDetailService;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 * Created by Алексей on 15.10.2016.
 */
@Configuration
@ComponentScan(basePackages="ua.ukma.nc")
@EnableWebMvc
@EnableTransactionManagement
@PropertySource("classpath:database.properties")
public class WebApp extends WebMvcConfigurerAdapter {

    @Autowired
    Environment env;
    
    @Bean
    public ViewResolver jspViewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/presentation/");
        resolver.setSuffix(".jsp");
        resolver.setContentType("text/html; charset=UTF-8");
        
        return resolver;
    }
    
    @Bean
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(manager);
 
        // Define all possible view resolvers
        List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
 
        resolvers.add(excelViewResolver());
        resolvers.add(jspViewResolver());
         
        resolver.setViewResolvers(resolvers);
        return resolver;
    }	
    
    @Bean
    public ViewResolver excelViewResolver() {
        return new ExcelViewResolver();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/presentation/**").addResourceLocations("/presentation/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
//        registry.addViewController("/403").setViewName("403");
//        registry.addViewController("/roles").setViewName("chooseRole");
    }

    @Bean
    public PlatformTransactionManager txManager() {
        DataSourceTransactionManager txManager = new DataSourceTransactionManager();
        txManager.setDataSource(getDataSource());
        return txManager;
    }

    @Bean
    public SecurityUserDetailService getUDS(){
        return new SecurityUserDetailService();
    }

    @Bean(name="dataSource")
    public DataSource getDataSource() {
        PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setServerName(env.getProperty("db.server.name"));
        dataSource.setDatabaseName(env.getProperty("db.name"));
        dataSource.setUser(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        dataSource.setMaxConnections(Integer.parseInt(env.getProperty("db.connections")));
        return dataSource;
    }

    @Bean()
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }

}