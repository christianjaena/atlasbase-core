package com.atlasbase.atlasbase_core;

import com.atlasbase.atlasbase_core.application.commands.UserSignInCommand;
import com.atlasbase.atlasbase_core.application.commands.UserSignUpCommand;
import com.atlasbase.atlasbase_core.application.dto.UserRequestDto;
import com.atlasbase.atlasbase_core.core.model.Metadata;
import com.atlasbase.atlasbase_core.core.model.User;
import com.atlasbase.atlasbase_core.infrastructure.persistence.entity.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestFixtures {

	public static String USER_CONTROLLER_BASE_PATH = "/api/v1/users";

	public static String signUpJsonContent = """
			{
			    "userName": "testuser",
			    "email": "user@example.com",
			    "password": "password",
			    "firstName": "Test",
			    "lastName": "User"
			}
			""";

	public static String signInJsonContent = """
			{
			    "userName": "testuser",
			    "password": "password"
			}
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

	public static UserEntity userEntityFromUserSignUpCommand(UserSignUpCommand command) {
		Metadata metadata = new Metadata();
		metadata.setCreateDate(Instant.now());
		metadata.setUpdateDate(Instant.now());
		metadata.setCreatedBy("SYSTEM");
		metadata.setUpdatedBy("SYSTEM");

		UserEntity user = new UserEntity();
		user.setId(UUID.randomUUID());
		user.setEmail(command.getEmail());
		user.setUserName(command.getUserName());
		user.setFirstName(command.getFirstName());
		user.setLastName(command.getLastName());
		user.setMetadata(metadata);
		user.setPassword(command.getPassword());
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

	public static UserRequestDto userRequestMock() {
		return new UserRequestDto("johndoe", "John", "Doe", "johndoe@gmail.com", "password");
	}

	public static UserSignUpCommand userSignUpCommandMock() {
		return new UserSignUpCommand("johndoe", "John", "Doe", "johndoe@gmail.com", "password");
	}

	public static UserSignInCommand userSignInCommandMock() {
		return new UserSignInCommand("johndoe", "password");
	}

}
