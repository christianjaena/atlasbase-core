package com.atlasbase.atlasbase_core.application.processors;

import com.atlasbase.atlasbase_core.TestFixtures;
import com.atlasbase.atlasbase_core.application.commands.BaseCommand;
import com.atlasbase.atlasbase_core.application.commands.UserSignInCommand;
import com.atlasbase.atlasbase_core.application.commands.UserSignUpCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserSignInProcessorTest {

	@InjectMocks
	private UserSignInProcessor processor;

	@Mock
	private AuthenticationManager manager;

	private UserSignInCommand command;

	@BeforeEach
	void setup() {
		command = TestFixtures.userSignInCommandMock();
	}

	@Test
	void givenUserIsFound_whenProcessed_thenDoNothing() {
		isAuthenticated(true);

		processor.process(command);
	}

	@Test
	void givenUserIsNotFound_whenProcessed_thenThrowBadCredentialsException() {
		isAuthenticated(false);

		assertThrows(BadCredentialsException.class, () -> processor.process(command));
	}

	private void isAuthenticated(boolean isAuthenticated) {
		Authentication authentication = mock(Authentication.class);
		when(manager.authenticate(any())).thenReturn(authentication);
		when(authentication.isAuthenticated()).thenReturn(isAuthenticated);
	}

}
