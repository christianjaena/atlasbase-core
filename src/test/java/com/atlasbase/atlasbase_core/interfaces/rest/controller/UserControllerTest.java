package com.atlasbase.atlasbase_core.interfaces.rest.controller;

import com.atlasbase.atlasbase_core.application.UserProcessManager;
import com.atlasbase.atlasbase_core.infrastructure.configuration.SecurityConfiguration;
import com.atlasbase.atlasbase_core.interfaces.rest.dto.UserRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
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


    @Nested
    class SignIn {

        @Test
        void givenWrongCredentials_whenSignIn_shouldReturnInvalidCredentials() throws Exception {
            doThrow(new BadCredentialsException("Invalid credentials"))
                    .when(processManager).manage(any(UserRequest.class), any(String.class));

            mockMvc.perform(post("/v1/users/sign-in")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {"email": "user@example.com", "password": "password"}
                                    """))
                    .andExpect(status().isUnauthorized())
                    .andExpect(content().string("Invalid credentials"));
        }

        @Test
        void givenCorrectCredentialsAndIsAuthenticatedTrue_whenSignIn_shouldReturnAuthenticated() throws Exception {
            doNothing().when(processManager).manage(any(UserRequest.class), any(String.class));

            mockMvc.perform(post("/v1/users/sign-in")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {"email": "user@example.com", "password": "password"}
                                    """))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Authenticated"));
        }

    }

    @Nested
    class SignUp {

        @Test
        void givenUserIsNotPresent_whenSignUp_shouldReturnCreated() throws Exception {

            mockMvc.perform(
                    post("/v1/users/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {"email": "user@example.com", "password": "password"}
                                    """)
                    )
                    .andExpect(status().isCreated());

        }

        @Test
        void givenUserIsPresent_whenSignUp_shouldReturnBadRequest() throws Exception {

            mockMvc.perform(
                            post("/v1/users/sign-up")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("""
                                    {"email": "user@example.com", "password": "password"}
                                    """)
                    )
                    .andExpect(status().isBadRequest());
        }
    }
}
