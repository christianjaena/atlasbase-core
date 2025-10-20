package com.atlasbase.atlasbase_core.interfaces.rest.controller;

import com.atlasbase.atlasbase_core.TestFixtures;
import com.atlasbase.atlasbase_core.application.UserProcessManager;
import com.atlasbase.atlasbase_core.application.constants.UserAction;
import com.atlasbase.atlasbase_core.application.dto.UserRequest;
import com.atlasbase.atlasbase_core.application.exceptions.UserEmailExistsException;
import com.atlasbase.atlasbase_core.application.exceptions.UserNameExistsException;
import com.atlasbase.atlasbase_core.application.exceptions.ValidationException;
import com.atlasbase.atlasbase_core.application.validators.UserValidator;
import com.atlasbase.atlasbase_core.infrastructure.configuration.SecurityConfiguration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Test
    void givenMalformedUserRequest_whenValidated_thenReturnBadRequest() throws Exception {
        doThrow(ValidationException.class).when(validator)
                .validate(any(UserRequest.class));

        mockMvc
                .perform(post(TestFixtures.USER_CONTROLLER_BASE_PATH + "/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestFixtures.jsonContent))
                .andExpect(status().isBadRequest());

        mockMvc
                .perform(post(TestFixtures.USER_CONTROLLER_BASE_PATH + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestFixtures.jsonContent))
                .andExpect(status().isBadRequest());
    }

	@Nested
	class SignIn {

		@Test
		void givenWrongCredentials_whenSignIn_thenReturnInvalidCredentials() throws Exception {
			doThrow(new BadCredentialsException("Invalid credentials")).when(processManager)
				.manage(any(UserRequest.class), any(UserAction.class));

			mockMvc
				.perform(post(TestFixtures.USER_CONTROLLER_BASE_PATH + "/sign-in")
					.contentType(MediaType.APPLICATION_JSON)
					.content(TestFixtures.jsonContent))
				.andExpect(status().isUnauthorized())
				.andExpect(content().string("Invalid credentials"));
		}

		@Test
		void givenCorrectCredentialsAndIsAuthenticatedTrue_whenSignIn_thenReturnAuthenticated() throws Exception {
			doNothing().when(processManager).manage(any(UserRequest.class), any(UserAction.class));

			mockMvc
				.perform(post(TestFixtures.USER_CONTROLLER_BASE_PATH + "/sign-in")
					.contentType(MediaType.APPLICATION_JSON)
					.content(TestFixtures.jsonContent))
				.andExpect(status().isOk())
				.andExpect(content().string("Authenticated"));
		}

	}

	@Nested
	class SignUp {

		@Test
		void givenUserIsNotPresent_whenSignUp_thenReturnCreated() throws Exception {
			mockMvc
				.perform(post(TestFixtures.USER_CONTROLLER_BASE_PATH + "/sign-up")
					.contentType(MediaType.APPLICATION_JSON)
					.content(TestFixtures.jsonContent))
				.andExpect(status().isCreated());
		}

		@Test
		void givenUserNameIsPresent_whenSignUp_thenReturnBadRequest() throws Exception {
			doThrow(new UserNameExistsException("UserName already exists")).when(validator)
				.validate(any(UserRequest.class));

			mockMvc
				.perform(post(TestFixtures.USER_CONTROLLER_BASE_PATH + "/sign-up")
					.contentType(MediaType.APPLICATION_JSON)
					.content(TestFixtures.jsonContent))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("UserName already exists"));
		}

		@Test
		void givenEmailIsPresent_whenSignUp_thenReturnBadRequest() throws Exception {
			doThrow(new UserEmailExistsException("Email already exists")).when(validator)
				.validate(any(UserRequest.class));

			mockMvc
				.perform(post(TestFixtures.USER_CONTROLLER_BASE_PATH + "/sign-up")
					.contentType(MediaType.APPLICATION_JSON)
					.content(TestFixtures.jsonContent))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("Email already exists"));
		}

	}

}
