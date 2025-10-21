package com.atlasbase.atlasbase_core;

import com.atlasbase.atlasbase_core.application.dto.UserRequest;
import com.atlasbase.atlasbase_core.core.model.Metadata;
import com.atlasbase.atlasbase_core.core.model.User;
import com.atlasbase.atlasbase_core.infrastructure.persistence.entity.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestFixtures {

	public static String USER_CONTROLLER_BASE_PATH = "/v1/users";

	public static String jsonContent = """
			{"email": "user@example.com", "password": "password"}
			""";

	public static User userMock() {
		Metadata metadata = new Metadata();
		metadata.setCreateDate(Instant.now());
		metadata.setUpdateDate(Instant.now());
		metadata.setCreatedBy("TEST");
		metadata.setUpdatedBy("TEST");

		User user = new User();
		user.setId(UUID.randomUUID());
		user.setEmail("testuser@gmail.com");
		user.setUserName("test-user");
		user.setFirstName("Test");
		user.setLastName("User");
		user.setMetadata(metadata);
		user.setPassword("password");
		return user;
	}

	public static UserEntity userEntityMock() {
		Metadata metadata = new Metadata();
		metadata.setCreateDate(Instant.now());
		metadata.setUpdateDate(Instant.now());
		metadata.setCreatedBy("TEST");
		metadata.setUpdatedBy("TEST");

		UserEntity user = new UserEntity();
		user.setId(UUID.randomUUID());
		user.setEmail("testuser@gmail.com");
		user.setUserName("test-user");
		user.setFirstName("Test");
		user.setLastName("User");
		user.setMetadata(metadata);
		user.setPassword("password");
		return user;
	}

	public static UserEntity userEntityFromUserRequestMock(UserRequest request) {
		Metadata metadata = new Metadata();
		metadata.setCreateDate(Instant.now());
		metadata.setUpdateDate(Instant.now());
		metadata.setCreatedBy("SYSTEM");
		metadata.setUpdatedBy("SYSTEM");

		UserEntity user = new UserEntity();
		user.setId(UUID.randomUUID());
		user.setEmail(request.email());
		user.setUserName(request.userName());
		user.setFirstName(request.firstName());
		user.setLastName(request.lastName());
		user.setMetadata(metadata);
		user.setPassword(request.password());
		return user;
	}

	public static Metadata createMetadata() {
		Metadata metadata = new Metadata();
		metadata.setCreateDate(Instant.now());
		metadata.setCreatedBy("SYSTEM");
		return metadata;
	}

	public static Metadata updateMetadata() {
		Metadata metadata = new Metadata();
		metadata.setUpdateDate(Instant.now());
		metadata.setUpdatedBy("SYSTEM");
		return metadata;
	}

	public static UserRequest userRequestMock() {
		return new UserRequest("johndoe", "John", "Doe", "johndoe@gmail.com", "password");
	}

}
