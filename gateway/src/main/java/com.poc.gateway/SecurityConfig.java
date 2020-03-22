package com.poc.gateway;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.poc.gateway.security.JwtTokenAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception
	{
		httpSecurity
			.csrf().disable()
			.logout().disable()
			.formLogin().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.anonymous()
			.and()
			.exceptionHandling().authenticationEntryPoint(
			(req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
			.and()
			.addFilterAfter(new JwtTokenAuthenticationFilter(),
				UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers("/login", "/register").permitAll()
			.antMatchers("/data/**").permitAll()
			.antMatchers("/data-service/**").permitAll()
			.antMatchers("/data-service/admin").hasRole("ADMIN")
			.antMatchers("/data-service/user").hasRole("USER")
			.antMatchers("/data-service/guest").permitAll();
	}
}

