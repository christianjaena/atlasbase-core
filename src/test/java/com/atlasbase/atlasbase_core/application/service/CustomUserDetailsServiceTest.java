package com.atlasbase.atlasbase_core.application.service;

import com.atlasbase.atlasbase_core.core.model.User;
import com.atlasbase.atlasbase_core.core.port.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

	@Mock
	private UserRepository repository;

	@InjectMocks
	private CustomUserDetailsService service;

	@Test
	void givenExistingUser_whenLoadByUserName_returnUserDetails() {
		User user = new User();
		user.setUserName("johndoe");
		user.setPassword("password");
		user.setEmail("johndoe@gmail.com");

		when(repository.findByUserName(any(String.class))).thenReturn(Optional.of(user));

		UserDetails userDetails = service.loadUserByUsername("johndoe");

		assertNotNull(userDetails);
		assertEquals("johndoe", userDetails.getUsername());
	}

	@Test
	void givenNonExistingUser_whenLoadByUserName_throwUsernameNotFoundException() {
		assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("nonexistinguser"));
	}

}
