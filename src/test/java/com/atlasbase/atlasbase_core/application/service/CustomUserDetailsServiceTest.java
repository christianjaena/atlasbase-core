package com.atlasbase.atlasbase_core.application.service;

import com.atlasbase.atlasbase_core.infrastructure.persistence.jpa.user.UserEntity;
import com.atlasbase.atlasbase_core.infrastructure.persistence.jpa.user.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserJpaRepository repository;

    @InjectMocks
    private CustomUserDetailsService service;

    @Test
    void givenExistingUser_whenLoadByUserName_returnUserDetails() {
        UserEntity user = UserEntity
                .builder()
                .userName("johndoe")
                .password("password")
                .email("johndoe@gmail.com")
                .build();

        when(repository.findByUserName(any(String.class))).thenReturn(Optional.of(user));

        UserDetails userDetails = service.loadUserByUsername("johndoe");

        assertNotNull(userDetails);
        assertEquals("johndoe", userDetails.getUsername());
    }

    @Test
    void givenNonExistingUser_whenLoadByUserName_throwUsernameNotFoundException() {
        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("nonexistinguser"));
    }
}
