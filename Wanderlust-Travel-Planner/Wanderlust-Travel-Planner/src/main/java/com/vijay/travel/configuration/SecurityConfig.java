package com.vijay.travel.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import com.vijay.travel.service.JwtTokenService;
import com.vijay.travel.web.auth.CustomLogoutSuccessHandler;
import com.vijay.travel.web.auth.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	protected CustomLogoutSuccessHandler customLogoutSuccessHandler;
	protected JwtTokenService jwtTokenService;

	@Autowired
	public void setCustomLogoutSuccessHandler(CustomLogoutSuccessHandler customLogoutSuccessHandler) {

		this.customLogoutSuccessHandler = customLogoutSuccessHandler;
	}

	@Autowired
	public void setJwtTokenService(JwtTokenService jwtTokenService) {

		this.jwtTokenService = jwtTokenService;
	}

	@Bean
	protected HttpStatusEntryPoint httpStatusEntryPoint() {

		return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
	}

	@Bean
	protected JwtFilter jwtFilter() {

		return new JwtFilter(jwtTokenService, httpStatusEntryPoint());
	}

	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http
				.authorizeHttpRequests(registry -> registry.requestMatchers("/users", "/login").permitAll().anyRequest()
						.authenticated())
				.addFilterBefore(jwtFilter(), LogoutFilter.class).csrf(CsrfConfigurer::disable)
				.cors(CorsConfigurer::disable).formLogin(AbstractHttpConfigurer::disable)
				.httpBasic(basic -> basic.authenticationEntryPoint(httpStatusEntryPoint()))
				.logout(logout -> logout.logoutSuccessHandler(customLogoutSuccessHandler).logoutUrl("/logout"))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();
	}

	@Bean
	protected PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}
}
