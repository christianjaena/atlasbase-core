package com.atlasbase.atlasbase_core.infrastructure.configuration;

import com.atlasbase.atlasbase_core.application.services.CustomUserDetailsService;
import com.atlasbase.atlasbase_core.application.services.JwtService;
import com.atlasbase.atlasbase_core.infrastructure.properties.CorsProperties;
import com.atlasbase.atlasbase_core.infrastructure.security.JwtAuthenticationFilter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableWebSecurity
@Configuration
@EnableConfigurationProperties(CorsProperties.class)
public class SecurityConfiguration {

	private final CorsProperties corsProperties;

	public SecurityConfiguration(CorsProperties corsProperties) {
		this.corsProperties = corsProperties;
	}

	@Bean
	@Order(1)
	public SecurityFilterChain publicSecurityChain(HttpSecurity http) throws Exception {
		http.securityMatcher("/api/v1/users/sign-in", "/api/v1/users/sign-up", "/api/v1/auth/**", "/api/v1/stripe",
				"/actuator/**")
			.csrf(AbstractHttpConfigurer::disable)
			.cors(Customizer.withDefaults())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

		return http.build();
	}

	@Bean
	@Order(2)
	public SecurityFilterChain securedSecurityChain(HttpSecurity http, JwtService jwtService,
			UserDetailsService userDetailsService) throws Exception {
		JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtService, userDetailsService);
		http.csrf(AbstractHttpConfigurer::disable)
			.cors(Customizer.withDefaults())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http,
			CustomUserDetailsService customUserDetailsService) throws Exception {
		AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

		authBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());

		return authBuilder.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(corsProperties.allowedOrigins());
		config.setAllowedMethods(corsProperties.allowedMethods());
		config.setAllowedHeaders(List.of("*"));
		config.setAllowCredentials(true);
		config.setMaxAge(corsProperties.maxAge());

		var source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

}
