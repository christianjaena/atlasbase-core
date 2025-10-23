package com.atlasbase.atlasbase_core.application.processors;

import com.atlasbase.atlasbase_core.application.exceptions.UserEmailExistsException;
import com.atlasbase.atlasbase_core.application.factory.UserFactory;
import com.atlasbase.atlasbase_core.application.interfaces.Processor;
import com.atlasbase.atlasbase_core.core.port.UserRepository;
import com.atlasbase.atlasbase_core.application.dto.UserRequest;
import com.atlasbase.atlasbase_core.infrastructure.persistence.entity.UserEntity;
import com.atlasbase.atlasbase_core.infrastructure.persistence.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserSignUpProcessor implements Processor<UserRequest> {

	private final UserRepository repository;

	private final UserMapper mapper;

	private final UserFactory factory;

	public UserSignUpProcessor(UserRepository repository, UserMapper mapper, UserFactory factory) {
		this.repository = repository;
		this.mapper = mapper;
		this.factory = factory;
	}

	@Override
	public void process(UserRequest request) {
		UserEntity userEntity = factory.createUserEntity(request);

		repository.save(mapper.toDomain(userEntity));
	}

}
