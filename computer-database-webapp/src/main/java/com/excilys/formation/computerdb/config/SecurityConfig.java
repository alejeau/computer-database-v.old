package com.excilys.formation.computerdb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.excilys.formation.computerdb.service.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	 @Override
	  public void configure(WebSecurity web) throws Exception {
	    web
	      .ignoring()
	         .antMatchers("/resources/**"); // allows login to access to the resources
	  }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  http.authorizeRequests()
		.antMatchers("/access/add/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/access/edit/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/access/delete/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
		.and()
			.formLogin()
			.loginPage("/login")
			.permitAll()
		    .usernameParameter("username")
		    .passwordParameter("password")
			.defaultSuccessUrl("/access")
		.and()
		    .logout()
//		    .logoutSuccessUrl("/login?logout");
		    .permitAll();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
}
