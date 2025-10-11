package com.atlasbase.atlasbase_core.interfaces.web.controller;

import com.atlasbase.atlasbase_core.TestFixtures;
import com.atlasbase.atlasbase_core.application.service.CustomUserDetailsService;
import com.atlasbase.atlasbase_core.domain.user.model.User;
import com.atlasbase.atlasbase_core.infrastructure.config.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    private User user;

    @BeforeEach
    void setup() {
        user = TestFixtures.userMock();
    }

    @Test
    void givenWrongCredentials_whenLogIn_shouldReturnInvalidCredentials() throws Exception {
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

}