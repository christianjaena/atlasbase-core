package com.atlasbase.atlasbase_core.interfaces.rest.controller;

import com.atlasbase.atlasbase_core.TestFixtures;
import com.atlasbase.atlasbase_core.application.commands.BaseCommand;
import com.atlasbase.atlasbase_core.application.managers.UserProcessManager;
import com.atlasbase.atlasbase_core.application.constants.UserAction;
import com.atlasbase.atlasbase_core.application.dto.UserRequestDto;
import com.atlasbase.atlasbase_core.application.exceptions.UserEmailExistsException;
import com.atlasbase.atlasbase_core.application.exceptions.UserNameExistsException;
import com.atlasbase.atlasbase_core.application.services.JwtService;
import com.atlasbase.atlasbase_core.application.validators.UserValidator;
import com.atlasbase.atlasbase_core.infrastructure.configuration.SecurityConfiguration;
import com.atlasbase.atlasbase_core.infrastructure.properties.JwtProperties;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(SecurityConfiguration.class)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private UserProcessManager processManager;

	@MockitoBean
	private UserValidator validator;

	@MockitoBean
	private AuthenticationManager manager;

	@MockitoBean
	private JwtService jwtService;

	@MockitoBean
	private UserDetailsService userDetailsService;

	@Nested
	class SignIn {

		@Test
		void givenWrongCredentials_whenSignIn_thenReturnInvalidCredentials() throws Exception {
			doThrow(new BadCredentialsException("Invalid credentials")).when(processManager)
				.manage(any(BaseCommand.class), any(UserAction.class));

			mockMvc
				.perform(post(TestFixtures.USER_CONTROLLER_BASE_PATH + "/sign-in")
					.contentType(MediaType.APPLICATION_JSON)
					.content(TestFixtures.signInJsonContent))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.error.message", is("Invalid credentials")));
		}

		@Test
		void givenCorrectCredentialsAndIsAuthenticatedTrue_whenSignIn_thenReturnAuthenticated() throws Exception {
			doNothing().when(processManager).manage(any(BaseCommand.class), any(UserAction.class));
			when(jwtService.generateToken(anyString())).thenReturn("mocked-jwt-token");

			mockMvc
				.perform(post(TestFixtures.USER_CONTROLLER_BASE_PATH + "/sign-in")
					.contentType(MediaType.APPLICATION_JSON)
					.content(TestFixtures.signInJsonContent))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.message", is("User Authenticated")))
				.andExpect(jsonPath("$.data.token").exists());
		}

	}

	@Nested
	class SignUp {

		@Test
		void givenUserIsNotPresent_whenSignUp_thenReturnCreated() throws Exception {

			when(jwtService.generateToken(anyString())).thenReturn("mocked-jwt-token");
			mockMvc
				.perform(post(TestFixtures.USER_CONTROLLER_BASE_PATH + "/sign-up")
					.contentType(MediaType.APPLICATION_JSON)
					.content(TestFixtures.signUpJsonContent))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.data.message", is("User Created")))
				.andExpect(jsonPath("$.data.token").exists());
		}

		@Test
		void givenUserNameIsPresent_whenSignUp_thenReturnBadRequest() throws Exception {
			doThrow(new UserNameExistsException("Username already exists")).when(validator)
				.validate(any(UserRequestDto.class));

			mockMvc
				.perform(post(TestFixtures.USER_CONTROLLER_BASE_PATH + "/sign-up")
					.contentType(MediaType.APPLICATION_JSON)
					.content(TestFixtures.signUpJsonContent))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error.message", is("Username already exists")));
		}

		@Test
		void givenEmailIsPresent_whenSignUp_thenReturnBadRequest() throws Exception {
			doThrow(new UserEmailExistsException("Email already exists")).when(validator)
				.validate(any(UserRequestDto.class));

			mockMvc
				.perform(post(TestFixtures.USER_CONTROLLER_BASE_PATH + "/sign-up")
					.contentType(MediaType.APPLICATION_JSON)
					.content(TestFixtures.signUpJsonContent))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error.message", is("Email already exists")));
		}

	}

}
