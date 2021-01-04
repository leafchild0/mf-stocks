package com.mf.appshell;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mf.appshell.security.JwtTokenAuthenticationFilter;
import com.mf.appshell.security.JwtTokenProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
	private JwtTokenProvider tokenProvider;

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
	{
	}

	@Override
	public void configure(WebSecurity web)
	{
		web.ignoring()
				.antMatchers( "/static/**","/resources/**", "/js/**", "/css/**", "/images/**");

	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity
			.csrf().disable()
			.logout().disable()
			.formLogin().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().anonymous()
			.and()
			.exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
			.and()
			.addFilterAfter(new JwtTokenAuthenticationFilter(tokenProvider),
				UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers("/auth/login", "/auth/register").permitAll()
			.antMatchers("/**",
				"/favicon.ico",
				"/**/*.png",
				"/**/*.gif",
				"/**/*.svg",
				"/**/*.jpg",
				"/**/*.html",
				"/**/*.css",
				"/**/*.js").permitAll()
			.antMatchers("/actuator/**").permitAll()
			.antMatchers("/static/**").permitAll()
			.antMatchers("/favicon.ico").permitAll()
			.antMatchers("/health-check").permitAll()
			.anyRequest()
			.authenticated();
		;
	}
}
