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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

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
    public ViewResolver getViewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/view/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/view/**").addResourceLocations("/view/");
    }

    @Bean
    public PlatformTransactionManager txManager() {
        DataSourceTransactionManager txManager = new DataSourceTransactionManager();
        txManager.setDataSource(getDataSource());
        return txManager;
    }


    @Bean
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

}