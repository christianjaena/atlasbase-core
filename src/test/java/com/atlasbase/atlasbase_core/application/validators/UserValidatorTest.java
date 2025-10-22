package com.atlasbase.atlasbase_core.application.validators;

import com.atlasbase.atlasbase_core.TestFixtures;
import com.atlasbase.atlasbase_core.application.dto.UserRequest;
import com.atlasbase.atlasbase_core.application.exceptions.UserEmailExistsException;
import com.atlasbase.atlasbase_core.application.exceptions.UserNameExistsException;
import com.atlasbase.atlasbase_core.core.port.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

	@InjectMocks
	private UserValidator validator;

	@Mock
	private UserRepository repository;

	private UserRequest userRequest;

	@BeforeEach
	void setup() {
		userRequest = TestFixtures.userRequestMock();
	}

	@Test
	void whenEmailIsExisting_whenValidate_thenThrowException() {
		when(repository.findByEmail(userRequest.email()))
			.thenThrow(new UserEmailExistsException("Email already " + "exists"));

		assertThrows(UserEmailExistsException.class, () -> validator.validate(userRequest));

		verify(repository).findByEmail(any(String.class));
	}

	@Test
	void whenUserNameIsExisting_whenValidate_thenThrowException() {
		when(repository.findByUserName(userRequest.userName()))
			.thenThrow(new UserNameExistsException("UserName already " + "exists"));

		assertThrows(UserNameExistsException.class, () -> validator.validate(userRequest));

		verify(repository).findByUserName(any(String.class));
	}

	@Test
	void whenUserNameAndEmailIsNonExisting_whenValidate_thenDoNothing() {
		validator.validate(userRequest);

		verify(repository).findByUserName(any(String.class));
		verify(repository).findByEmail(any(String.class));
	}

}