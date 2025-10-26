package com.atlasbase.atlasbase_core.interfaces.rest.controller;

import com.atlasbase.atlasbase_core.application.commands.UserSignInCommand;
import com.atlasbase.atlasbase_core.application.commands.UserSignUpCommand;
import com.atlasbase.atlasbase_core.application.managers.UserProcessManager;
import com.atlasbase.atlasbase_core.application.constants.UserAction;
import com.atlasbase.atlasbase_core.application.dto.UserRequestDto;
import com.atlasbase.atlasbase_core.application.validators.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserProcessManager processManager;

	private final UserValidator validator;

	public UserController(UserProcessManager processManager, UserValidator validator) {
		this.processManager = processManager;
		this.validator = validator;
	}

	@PostMapping("/sign-in")
	public ResponseEntity<String> signIn(@RequestBody UserRequestDto body) {
		UserSignInCommand command = UserSignInCommand.builder()
			.userName(body.userName())
			.password(body.password())
			.build();
		processManager.manage(command, UserAction.SIGN_IN);
		return ResponseEntity.status(HttpStatus.OK).body("Authenticated");
	}

	@PostMapping("/sign-up")
	public ResponseEntity<String> signUp(@RequestBody UserRequestDto body) {
		validator.validate(body);
		UserSignUpCommand command = UserSignUpCommand.builder()
			.userName(body.userName())
			.password(body.password())
			.firstName(body.firstName())
			.lastName(body.lastName())
			.email(body.email())
			.build();
		processManager.manage(command, UserAction.SIGN_UP);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
