package ua.ukma.nc.config;

import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by Алексей on 05.11.2016.
 */
@Configuration
@ComponentScan(basePackages={"ua.ukma.nc"}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class) })
@EnableTransactionManagement
@PropertySource("classpath:database.properties")
public class TestConfig {

    @Autowired
    Environment env;
    
    

    @Bean
    public PlatformTransactionManager txManager() {
        DataSourceTransactionManager txManager = new DataSourceTransactionManager();
        txManager.setDataSource(getDataSource());
        return txManager;
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
}
