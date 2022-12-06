package com.sbms.SecurityConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sbms.JWTConfiguration.JWTFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	JWTFilter jwtFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
	
	@Bean /* (name = BeanIds.AUTHENTICATION_MANAGER) */
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		http.authorizeRequests().antMatchers("/sbms/irctc/deleteTicket/**").hasAnyRole("ADMIN").and()
			.authorizeRequests().antMatchers("/sbms/irctc//getToken").authenticated().and()
			.authorizeRequests().antMatchers("/sbms/irctc/bookTicket").authenticated().and()
			.authorizeRequests().antMatchers("/sbms/irctc/updateTicket").authenticated().and()
			.authorizeRequests().antMatchers("/sbms/irctc/getTicket/**").authenticated().and()
//			.authorizeRequests().antMatchers("/sbms/irctc/home").permitAll().and()
			.authorizeRequests().antMatchers("/sbms/irctc/welcome").hasAnyRole("ADMIN").and()
//			.authorizeRequests().antMatchers("/sbms/irctc/train_search").permitAll().and()
			.formLogin().and()
			.httpBasic().and()
			.exceptionHandling().and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	}

}
