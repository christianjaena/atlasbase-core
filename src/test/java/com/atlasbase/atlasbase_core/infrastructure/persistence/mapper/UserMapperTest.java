package com.atlasbase.atlasbase_core.infrastructure.persistence.mapper;

import com.atlasbase.atlasbase_core.core.model.User;
import com.atlasbase.atlasbase_core.infrastructure.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

	private final UserMapper mapper = new UserMapper();

	@Test
	void givenDomainIsPresent_whenToEntity_thenReturnEntity() {
		User user = new User();
		user.setUserName("johndoe");
		user.setEmail("johndoe@gmail.com");

		UserEntity entity = mapper.toEntity(user);

		assertNotNull(entity);
		assertEquals("johndoe", entity.getUserName());
		assertEquals("johndoe@gmail.com", entity.getEmail());
	}

	@Test
	void givenDomainIsNull_whenToEntity_thenReturnNull() {
		User user = null;

		UserEntity userEntity = mapper.toEntity(user);

		assertNull(userEntity);
	}

	@Test
	void givenEntityIsPresent_whenToDomain_thenReturnDomain() {
		UserEntity entity = new UserEntity();
		entity.setUserName("johndoe");
		entity.setEmail("johndoe@gmail.com");

		User user = mapper.toDomain(entity);

		assertNotNull(entity);
		assertEquals("johndoe", user.getUserName());
		assertEquals("johndoe@gmail.com", user.getEmail());
	}

	@Test
	void givenEntityIsNull_whenToDomain_thenReturnDomain() {
		UserEntity entity = null;

		User domain = mapper.toDomain(entity);

		assertNull(domain);
	}

}
