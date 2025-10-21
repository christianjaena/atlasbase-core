package com.atlasbase.atlasbase_core.application.factory;

import com.atlasbase.atlasbase_core.application.dto.UserRequest;
import com.atlasbase.atlasbase_core.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {

	private final MetadataFactory metadataFactory;

	public UserFactory(MetadataFactory metadataFactory) {
		this.metadataFactory = metadataFactory;
	}

	public UserEntity createUserEntity(UserRequest request) {
		return UserEntity.builder()
			.userName(request.userName())
			.email(request.email())
			.firstName(request.firstName())
			.lastName(request.lastName())
			.password(request.password())
			.metadata(metadataFactory.create())
			.build();
	}

}
