package com.atlasbase.atlasbase_core.infrastructure.persistence.adapter;

import com.atlasbase.atlasbase_core.TestFixtures;
import com.atlasbase.atlasbase_core.core.model.User;
import com.atlasbase.atlasbase_core.infrastructure.persistence.entity.UserEntity;
import com.atlasbase.atlasbase_core.infrastructure.persistence.repository.UserJpaRepository;
import com.atlasbase.atlasbase_core.infrastructure.persistence.mapper.UserMapper;
import com.atlasbase.atlasbase_core.infrastructure.persistence.repository.UserRepositoryJpaAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserJpaRepositoryAdapterTest {

	@InjectMocks
	private UserRepositoryJpaAdapter repository;

	@Mock
	private UserJpaRepository userJpaRepository;

	@Mock
	private UserMapper userMapper;

	@Test
	void givenExistingUser_whenFindByUserName_thenReturnUser() {
		UserEntity userEntityMock = TestFixtures.userEntityMock();
		User userMock = TestFixtures.userMock();

		when(userJpaRepository.findByUserName(any(String.class))).thenReturn(Optional.of(userEntityMock));
		when(userMapper.toDomain(userEntityMock)).thenReturn(userMock);

		Optional<User> user = repository.findByUserName("johndoe");

		assertEquals(user.get(), userMock);
	}

	@Test
	void givenNonExistingUser_whenFindByUserName_thenReturnEmpty() {
		when(userJpaRepository.findByUserName(any(String.class))).thenReturn(Optional.empty());

		Optional<User> user = repository.findByUserName("johndoe");

		assertTrue(user.isEmpty());
	}

	@Test
	void givenExistingUser_whenFindByEmail_thenReturnUser() {
		UserEntity userEntityMock = TestFixtures.userEntityMock();
		User userMock = TestFixtures.userMock();

		when(userJpaRepository.findByEmail(any(String.class))).thenReturn(Optional.of(userEntityMock));
		when(userMapper.toDomain(userEntityMock)).thenReturn(userMock);

		Optional<User> user = repository.findByEmail("johndoe@gmail.com");

		assertEquals(user.get(), userMock);
	}

	@Test
	void givenNonExistingUser_whenFindByEmail_thenReturnEmpty() {
		when(userJpaRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

		Optional<User> user = repository.findByEmail("john@gmail.com");

		assertTrue(user.isEmpty());
	}

}