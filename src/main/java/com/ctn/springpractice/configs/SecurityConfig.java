package com.ctn.springpractice.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
	@Autowired
	private AuthenticationProvider authenticationProvider;
	@Autowired
	private JwtAuthenticationFilter jwtAuthFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception {
		
		 http 
		  .csrf() 
		  .disable() 
		  .authorizeHttpRequests() 
		  .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/api/v1/auth/**")
		  .permitAll()
		  .anyRequest()
		  .fullyAuthenticated()
		  .and() 
		  .sessionManagement()
		  .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		  .and()
		  .authenticationProvider(authenticationProvider)
		  .addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
