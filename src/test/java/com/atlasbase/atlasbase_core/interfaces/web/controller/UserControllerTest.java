package com.atlasbase.atlasbase_core.interfaces.web.controller;

import com.atlasbase.atlasbase_core.infrastructure.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenWrongCredentials_whenLogIn_shouldReturnInvalidCredentials() throws Exception {
        mockMvc.perform(post("/v1/users/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email": "user@example.com", "password": "password"}
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid credentials"));

    }

}