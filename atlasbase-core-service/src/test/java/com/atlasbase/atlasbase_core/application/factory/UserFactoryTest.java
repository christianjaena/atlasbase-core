package com.atlasbase.atlasbase_core.application.factory;

import com.atlasbase.atlasbase_core.TestFixtures;
import com.atlasbase.atlasbase_core.application.dto.UserRequest;
import com.atlasbase.atlasbase_core.core.model.Metadata;
import com.atlasbase.atlasbase_core.infrastructure.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserFactoryTest {

	@InjectMocks
	private UserFactory factory;

	@Mock
	private MetadataFactory metadataFactory;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Test
	void givenUserRequest_whenCreate_thenReturnUserEntity() {
		UserRequest request = new UserRequest("johndoe", "John", "Doe", "johndoe@gmail.com", "password");
		UserEntity entityMock = TestFixtures.userEntityFromUserRequestMock(request);

		Metadata metadata = TestFixtures.createMetadata();
		when(metadataFactory.create()).thenReturn(metadata);
		when(passwordEncoder.encode(any(String.class))).thenReturn("password");

		UserEntity entity = factory.createUserEntity(request);

		assertNotNull(entity);
		assertNotNull(entity.getId());
		assertEquals(entity.getUserName(), entityMock.getUserName());
		assertEquals(entity.getFirstName(), entityMock.getFirstName());
		assertEquals(entity.getLastName(), entityMock.getLastName());
		assertEquals(entity.getEmail(), entityMock.getEmail());
		assertEquals(entity.getPassword(), entityMock.getPassword());
		assertEquals(entity.getMetadata(), metadata);

		verify(passwordEncoder).encode(any(String.class));
		verify(metadataFactory).create();
	}

}