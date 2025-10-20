package com.atlasbase.atlasbase_core.application.processors;

import com.atlasbase.atlasbase_core.application.exceptions.UserNameExistsException;
import com.atlasbase.atlasbase_core.application.interfaces.Processor;
import com.atlasbase.atlasbase_core.core.model.User;
import com.atlasbase.atlasbase_core.core.port.UserRepository;
import com.atlasbase.atlasbase_core.application.dto.UserRequest;
import org.springframework.stereotype.Component;

@Component
public class UserSignUpProcessor implements Processor<UserRequest> {

	private final UserRepository repository;

	public UserSignUpProcessor(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public void process(UserRequest type) {
		User userEntity = new User();
		repository.save(userEntity);
	}

}
