package com.atlasbase.atlasbase_core.application.factory;

import com.atlasbase.atlasbase_core.application.dto.UserRequest;
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

	public UserEntity createUserEntity(UserRequest request) {
		return UserEntity.builder()
			.id(UUID.randomUUID())
			.userName(request.userName())
			.email(request.email())
			.firstName(request.firstName())
			.lastName(request.lastName())
			.password(passwordEncoder.encode(request.password()))
			.metadata(metadataFactory.create())
			.build();
	}

}
