package com.atlasbase.atlasbase_core.application.processors;

import com.atlasbase.atlasbase_core.TestFixtures;
import com.atlasbase.atlasbase_core.application.exceptions.UserEmailExistsException;
import com.atlasbase.atlasbase_core.application.exceptions.UserNameExistsException;
import com.atlasbase.atlasbase_core.core.model.User;
import com.atlasbase.atlasbase_core.core.port.UserRepository;
import com.atlasbase.atlasbase_core.application.dto.UserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserSignUpProcessorTest {

	@InjectMocks
	private UserSignUpProcessor processor;

	@Mock
	private UserRepository repository;

	@Test
	void givenUserIsNotPresent_whenProcessed_shouldSaveUser() {
		UserRequest userRequest = TestFixtures.userRequestMock();

		processor.process(userRequest);

		verify(repository).save(any(User.class));
	}

	@Test
	void givenUserEmailIsExisting_whenProcessed_throwException() {
		UserRequest userRequest = TestFixtures.userRequestMock();

		when(repository.findByEmail(any(String.class)))
			.thenThrow(new UserEmailExistsException("User email already " + "exists"));

		assertThrows(UserEmailExistsException.class, () -> {
			processor.process(userRequest);
			verifyNoInteractions(repository);
		});
	}

	@Test
	void givenUserNameIsExisting_whenProcessed_throwException() {
		UserRequest userRequest = TestFixtures.userRequestMock();

		when(repository.findByUserName(any(String.class)))
			.thenThrow(new UserNameExistsException("UserName already " + "exists"));

		assertThrows(UserNameExistsException.class, () -> {
			processor.process(userRequest);
			verifyNoInteractions(repository);
		});
	}

}