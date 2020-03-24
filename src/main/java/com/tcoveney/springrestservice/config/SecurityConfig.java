package com.tcoveney.springrestservice.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private static final Logger logger = LogManager.getLogger(SecurityConfig.class);

	@Autowired
	private DataSource dataSource;
	
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
        .dataSource(dataSource);
        
        if (null == dataSource) {
        	logger.debug("dataSource is NULL");
        }
        else {
        	logger.debug("dataSource is NOT null");
        	Properties properties = dataSource.getConnection().getClientInfo();
        	logger.debug("Number of connection properties: " + properties.size());
        }
    }
    
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
          .csrf().disable()
          .authorizeRequests()
          .antMatchers("/api/**").hasRole("ADMIN")
//          .antMatchers("/anonymous*").anonymous()
//          .antMatchers("/login*").permitAll()
          .anyRequest().authenticated();
//          .and()
//          .formLogin()
//          .loginPage("/login.html")
//          .loginProcessingUrl("/perform_login")
//          .defaultSuccessUrl("/homepage.html", true)
//          //.failureUrl("/login.html?error=true")
//          .failureHandler(authenticationFailureHandler())
//          .and()
//          .logout()
//          .logoutUrl("/perform_logout")
//          .deleteCookies("JSESSIONID")
//          .logoutSuccessHandler(logoutSuccessHandler());
    }

}
