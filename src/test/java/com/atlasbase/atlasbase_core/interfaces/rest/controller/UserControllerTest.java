package com.atlasbase.atlasbase_core.interfaces.rest.controller;

import com.atlasbase.atlasbase_core.TestFixtures;
import com.atlasbase.atlasbase_core.application.service.CustomUserDetailsService;
import com.atlasbase.atlasbase_core.domain.model.User;
import com.atlasbase.atlasbase_core.infrastructure.configuration.SecurityConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SecurityConfiguration.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void givenWrongCredentials_whenSignIn_shouldReturnInvalidCredentials() throws Exception {
        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

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
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);

        mockMvc.perform(post("/v1/users/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email": "user@example.com", "password": "password"}
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("Authenticated"));
    }

    @Test
    void givenCorrectCredentialsAndIsAuthenticatedFalse_whenSignIn_shouldReturnUnauthenticated() throws Exception {
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        mockMvc.perform(post("/v1/users/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email": "user@example.com", "password": "password"}
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Unauthenticated"));
    }

}