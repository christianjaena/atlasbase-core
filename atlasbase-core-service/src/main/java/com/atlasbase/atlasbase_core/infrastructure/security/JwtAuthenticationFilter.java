package com.atlasbase.atlasbase_core.infrastructure.security;

import com.atlasbase.atlasbase_core.application.services.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;

	private final UserDetailsService userDetailsService;

	public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String token = extractJwtFromHeader(request);
			String userName = extractUserName(token);

			if (SecurityContextHolder.getContext().getAuthentication() != null) {
				throw new JwtException("Authentication context is not null");
			}

			UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

			if (!jwtService.validateToken(token)) {
				throw new JwtException("Error validating token");
			}
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());

			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		catch (JwtException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json");
			response.getWriter().write("""
					{ "error": "Invalid token", "message": "%s" }
					 """.formatted(e.getMessage()));
			return;
		}

		filterChain.doFilter(request, response);
	}

	private String extractUserName(String token) {
		String userName = jwtService.extractUsername(token);
		if (userName == null) {
			throw new JwtException("Failed to extract username from token");
		}
		return userName;
	}

	private String extractJwtFromHeader(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
			throw new JwtException("Authorization header should contain 'Bearer'");
		}
		return bearerToken.substring(7);
	}

}
