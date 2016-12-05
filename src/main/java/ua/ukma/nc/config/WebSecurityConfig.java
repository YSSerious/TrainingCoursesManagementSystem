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
                    .antMatchers("/role_def").hasRole("TEMP")
                    .antMatchers("/manageRoles", "/category", "/addCategory", "/saveCriteria", "/deleteCriteria",
                            "/deleteCategory", "/editCategory", "/updateProjectStartDate", "/updateProjectFinishDate",
                            "/updateProjectDescription", "/projects/delete",
                            "/getAvailableCriteria", "/addCriteria", "/deleteProjectCriteria",
                            "/getCriteriaAndGroups", "/saveMeeting", "/groups/add", "/groups/edit", "/groups/delete",
                            "/groups/removeMentor", "/groups/editMeeting", "/groups/deleteMeeting",
                            "/groups/add/*", "/create-meeting", "/getAvailableMeetingCriteria", "/addMeetingCriteria",
                            "/deleteMeetingCriteria", "/projects/add", "/manageRoles",
                            "/students/inactive", "/mentors/free").hasRole("ADMIN")
                    .antMatchers("/roles", "/set_role").hasAnyRole("ADMIN", "MENTOR", "HR", "TEMP")
                    .antMatchers("/","/projects", "/users/*", "/cookie", "/certainProject", "/projectAttachment/*",
                            "/ajaxstudetprofile", "/ajaxstudentprojects", "/ajaxmentorprojects", "/ajaxcriteria",
                             "/groups/addAttachment", "/groups/groupAttachment/*", "/groups/deleteAttachment",
                            "/meeting/*", "/ajaxcategories", "/allUsers", "/groups/group", "/isMeetingReviewed").authenticated()
                .antMatchers("/ajax/get/projects_final_review", "/ajax/get/final_review_form",
                        "/ajax/post/final_review_form/*", "/ajax/pot/evaluate/*", "/ajax/post/absent/*").hasRole("MENTOR")
                .antMatchers("/ajax/get/hr_review_projects", "/ajax/get/hr_review", "/ajax/post/hr_review").hasRole("HR")
                    .antMatchers("/reports/*", "/projectReport", "/groupReport", "/studentMarks", "/reports/*",
                            "/addProjectAttachment", "/removeProjectAttachment").hasAnyRole("HR", "ADMIN")
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/roles_def", true)
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
