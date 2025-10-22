package com.atlasbase.atlasbase_core.application.validators;

import com.atlasbase.atlasbase_core.application.dto.UserRequest;
import com.atlasbase.atlasbase_core.application.exceptions.UserEmailExistsException;
import com.atlasbase.atlasbase_core.application.interfaces.Validator;
import com.atlasbase.atlasbase_core.core.port.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserValidator implements Validator<UserRequest> {

	public final UserRepository repository;

	public UserValidator(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public void validate(UserRequest request) {
		repository.findByEmail(request.email()).ifPresent(_ -> {
			throw new UserEmailExistsException("Email already exists");
		});

		repository.findByUserName(request.userName()).ifPresent(_ -> {
			throw new UserEmailExistsException("UserName already exists");
		});
	}

}
