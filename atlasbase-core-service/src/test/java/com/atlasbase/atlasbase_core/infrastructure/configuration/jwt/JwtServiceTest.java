package com.atlasbase.atlasbase_core.infrastructure.configuration.jwt;

import com.atlasbase.atlasbase_core.application.services.JwtService;
import com.atlasbase.atlasbase_core.infrastructure.properties.JwtProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private JwtProperties jwtProperties;

    @BeforeEach
    void setUp(TestInfo info) {
        when(jwtProperties.secret()).thenReturn("supersecretkeysupersecretkey12345");
        if (!info.getDisplayName().equals("skipSetup")) {
            when(jwtProperties.issuer()).thenReturn("atlasbase");
            when(jwtProperties.expiration()).thenReturn(90000L);
        }
    }

    @Test
    void givenUsername_shouldGenerateToken() {
        String username = "testUser";
        String token = jwtService.generateToken(username);
        assertNotNull(token);
    }

    @Test
    void givenToken_shouldExtractUsername() {
        String username = "testUser";
        String token = jwtService.generateToken(username);
        String extracted = jwtService.extractUsername(token);
        assertEquals(username, extracted);
    }

    @Test
    void givenValidToken_shouldValidateToken() {
        String username = "testUser";
        String token = jwtService.generateToken(username);
        assertTrue(jwtService.validateToken(token));
    }

    @Test
    @DisplayName("skipSetup")
    void givenInvalidToken_shouldValidateToken() {
        String malformedToken = "this.is.not.a.jwt";
        assertFalse(jwtService.validateToken(malformedToken));
    }

}