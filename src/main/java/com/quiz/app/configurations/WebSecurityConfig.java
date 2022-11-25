package com.quiz.app.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.quiz.app.security.AuthTokenFilter;
import com.quiz.app.security.JwtUtils;
import com.quiz.app.services.user.UserDetailsServiceImpl;

import lombok.AllArgsConstructor;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class WebSecurityConfig {

	private JwtUtils jwtUtils;
	private UserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

	http.cors().and().csrf().disable();
	http.authenticationProvider(authenticationProvider());
	http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	return http.build();
	}

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
	  return new AuthTokenFilter(jwtUtils,userDetailsServiceImpl );
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		 
		authProvider.setUserDetailsService(userDetailsServiceImpl);
		authProvider.setPasswordEncoder(passwordEncoder());
	 
		return authProvider;
	}


	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
	  return authConfig.getAuthenticationManager();
	}


}
