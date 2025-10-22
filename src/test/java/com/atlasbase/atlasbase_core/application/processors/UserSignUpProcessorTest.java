package com.atlasbase.atlasbase_core.application.processors;

import com.atlasbase.atlasbase_core.TestFixtures;
import com.atlasbase.atlasbase_core.application.dto.UserRequest;
import com.atlasbase.atlasbase_core.application.factory.UserFactory;
import com.atlasbase.atlasbase_core.core.model.User;
import com.atlasbase.atlasbase_core.core.port.UserRepository;
import com.atlasbase.atlasbase_core.infrastructure.persistence.entity.UserEntity;
import com.atlasbase.atlasbase_core.infrastructure.persistence.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserSignUpProcessorTest {

	@InjectMocks
	private UserSignUpProcessor processor;

	@Mock
	private UserRepository repository;

	@Mock
	private UserFactory factory;

	@Mock
	private UserMapper mapper;

	@Test
	void givenUserIsNotPresent_whenProcessed_thenSaveUser() {
		UserRequest userRequest = TestFixtures.userRequestMock();
		User user = TestFixtures.userMock();
		UserEntity userEntity = TestFixtures.userEntityMock();

		when(factory.createUserEntity(any(UserRequest.class))).thenReturn(userEntity);
		when(mapper.toDomain(any(UserEntity.class))).thenReturn(user);

		processor.process(userRequest);

		verify(repository).save(user);
	}

}