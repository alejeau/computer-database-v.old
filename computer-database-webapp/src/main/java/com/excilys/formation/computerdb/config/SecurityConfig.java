package com.excilys.formation.computerdb.config;

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
		    .loginProcessingUrl("/login")
		    .usernameParameter("username")
		    .passwordParameter("password")
			.defaultSuccessUrl("/access")
		.and()
		    .logout()
		    .permitAll()
	  	.and()
	  		.csrf().disable();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
}
