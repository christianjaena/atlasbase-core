package com.atlasbase.atlasbase_core.application.factory;

import com.atlasbase.atlasbase_core.application.commands.UserSignUpCommand;
import com.atlasbase.atlasbase_core.application.dto.UserRequestDto;
import com.atlasbase.atlasbase_core.infrastructure.persistence.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserFactory {

	private final MetadataFactory metadataFactory;

	private final PasswordEncoder passwordEncoder;

	public UserFactory(MetadataFactory metadataFactory, PasswordEncoder passwordEncoder) {
		this.metadataFactory = metadataFactory;
		this.passwordEncoder = passwordEncoder;
	}

	public UserEntity createUserEntity(UserSignUpCommand command) {
		return UserEntity.builder()
			.id(UUID.randomUUID())
			.userName(command.getUserName())
			.email(command.getEmail())
			.firstName(command.getFirstName())
			.lastName(command.getLastName())
			.password(passwordEncoder.encode(command.getPassword()))
			.metadata(metadataFactory.create())
			.build();
	}

}
