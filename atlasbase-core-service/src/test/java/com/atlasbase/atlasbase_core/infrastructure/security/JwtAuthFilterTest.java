package com.atlasbase.atlasbase_core.infrastructure.security;

import com.atlasbase.atlasbase_core.application.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthFilterTest {

	@InjectMocks
	private JwtAuthFilter jwtAuthFilter;

	@Mock
	private JwtService jwtService;

	@Mock
	private UserDetailsService userDetailsService;

	private final String validToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
			+ ".eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private FilterChain filterChain;

	private UserDetails userDetails;

	private StringWriter writer;

	private PrintWriter printWriter;

	@BeforeEach
	void setup() {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		filterChain = mock(FilterChain.class);
		userDetails = mock(UserDetails.class);
		writer = new StringWriter();
		printWriter = new PrintWriter(writer);
	}

	@Test
	void givenInvalidRequestHeader_whenFilterInternal_doThrowException() throws ServletException, IOException {
		String errorResponse = """
				{ "error": "Invalid token", "message": "Authorization header should contain 'Bearer'" }
				""";
		when(request.getHeader("Authorization")).thenReturn("Invalid");
		when(response.getWriter()).thenReturn(mock(PrintWriter.class));

		jwtAuthFilter.doFilterInternal(request, response, filterChain);

		verifyErrorResponse(errorResponse);
		verifyNoInteractions(jwtService);
		verifyNoInteractions(userDetailsService);
		verifyNoInteractions(filterChain);
	}

	@Test
	void givenValidRequestHeaderAndUsernameCantBeExtractedFromToken_whenFilterInternal_doThrowException()
			throws ServletException, IOException {
		when(request.getHeader("Authorization")).thenReturn(validToken);
		when(response.getWriter()).thenReturn(mock(PrintWriter.class));
		when(jwtService.extractUsername(any(String.class))).thenReturn(null);

		jwtAuthFilter.doFilterInternal(request, response, filterChain);

		verify(jwtService).extractUsername(any(String.class));
		verifyNoInteractions(userDetailsService);
		verifyNoInteractions(filterChain);
	}

	@Test
	void givenSecurityContextIsNotNull_whenFilterInternal_doThrowException() throws ServletException, IOException {
		String errorResponse = """
				{ "error": "Invalid token", "message": "Authentication context is not null" }
				""";

		mockSecurityContext(true);
		when(request.getHeader("Authorization")).thenReturn(validToken);
		when(response.getWriter()).thenReturn(mock(PrintWriter.class));
		when(jwtService.extractUsername(any(String.class))).thenReturn("test");

		jwtAuthFilter.doFilterInternal(request, response, filterChain);

		verifyErrorResponse(errorResponse);
		verify(jwtService).extractUsername(any(String.class));
		verifyNoInteractions(userDetailsService);
		verifyNoInteractions(filterChain);

	}

	@Test
	void givenCantValidateToken_whenFilterInternal_doThrowException() throws ServletException, IOException {
		String errorResponse = """
				{ "error": "Invalid token", "message": "Error validating token" }
				""";
		mockSecurityContext(false);
		when(request.getHeader("Authorization")).thenReturn(validToken);
		when(response.getWriter()).thenReturn(mock(PrintWriter.class));
		when(jwtService.extractUsername(any(String.class))).thenReturn("test");
		when(userDetailsService.loadUserByUsername(any(String.class))).thenReturn(userDetails);
		when(jwtService.validateToken(any(String.class))).thenReturn(false);

		jwtAuthFilter.doFilterInternal(request, response, filterChain);

		verifyErrorResponse(errorResponse);
		verify(jwtService).extractUsername(any(String.class));
		verify(userDetailsService).loadUserByUsername(any(String.class));
		verify(jwtService).validateToken(any(String.class));
		verifyNoInteractions(filterChain);
	}

	@Test
	void givenValidRequestHeader_whenFilterInternal_shouldProceed() throws ServletException, IOException {
		mockSecurityContext(false);
		when(request.getHeader("Authorization")).thenReturn(validToken);
		when(jwtService.extractUsername(any(String.class))).thenReturn("test");
		when(userDetailsService.loadUserByUsername(any(String.class))).thenReturn(userDetails);
		when(jwtService.validateToken(any(String.class))).thenReturn(true);

		jwtAuthFilter.doFilterInternal(request, response, filterChain);

		verify(jwtService).extractUsername(any(String.class));
		verify(userDetailsService).loadUserByUsername(any(String.class));
		verify(jwtService).validateToken(any(String.class));
		verify(filterChain).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
	}

	private void verifyErrorResponse(String errorResponse) throws IOException {
		verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		verify(response).setContentType("application/json");
		verify(response.getWriter()).write(errorResponse);
	}

	private static void mockSecurityContext(boolean isPresent) {
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(isPresent ? mock(Authentication.class) : null);
		SecurityContextHolder.setContext(securityContext);
	}

}