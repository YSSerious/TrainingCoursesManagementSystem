package ua.ukma.nc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ua.ukma.nc.controller.auth.SecurityUserDetailService;

import javax.sql.DataSource;


/**
 * Created by Alex_Frankiv on 1.11.2016.
 */
@Configuration
@ComponentScan(basePackages="ua.ukma.nc")
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    SecurityUserDetailService securityUserDetailService;
    @Autowired
    PasswordEncoder passwordEncoder;

    //dataSource-based authentication
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(securityUserDetailService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeRequests()
                    .antMatchers("/", "/home").permitAll()
                    .antMatchers("/cookie").hasAnyRole("ADMIN", "MENTOR", "HR", "STUDENT")
                    .antMatchers("/projects").hasAnyRole("ADMIN", "MENTOR", "HR")
                    .antMatchers("/getuser").hasRole("ADMIN")     //for testing
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/cookie")
                    .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login?logout")
                    .deleteCookies("JSESSIONID")
                    .deleteCookies("tcms-chosen-role")
                    .invalidateHttpSession(true)
                    .and()
                .rememberMe()
                    .key("rem-me-key")
                    .rememberMeParameter("tcms-remember-me-param")
                    .rememberMeCookieName("tcms-remember-me")
                    .tokenValiditySeconds(86400)
                    .and()
                .exceptionHandling()
                    .accessDeniedPage("/403")
                .and()
                .csrf().disable();
    }

}
