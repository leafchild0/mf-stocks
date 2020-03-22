package com.poc.gateway;

import javax.servlet.http.HttpServletResponse;

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
			.antMatchers("/auth/login", "/auth/register").permitAll()
//			.antMatchers("/data/**").permitAll()
			.anyRequest()
			.authenticated();
//			.antMatchers("/data/admin").hasRole("ADMIN")
//			.antMatchers("/data/user").hasRole("USER")
					;
	}
}

