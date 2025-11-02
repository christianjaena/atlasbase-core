package com.atlasbase.atlasbase_core.interfaces.rest.controller;

import com.atlasbase.atlasbase_core.application.commands.UserSignInCommand;
import com.atlasbase.atlasbase_core.application.commands.UserSignUpCommand;
import com.atlasbase.atlasbase_core.application.constants.UserAction;
import com.atlasbase.atlasbase_core.application.dto.UserRequestDto;
import com.atlasbase.atlasbase_core.application.dto.ResponseDto;
import com.atlasbase.atlasbase_core.application.managers.UserProcessManager;
import com.atlasbase.atlasbase_core.application.services.JwtService;
import com.atlasbase.atlasbase_core.application.validators.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserProcessManager processManager;

	private final UserValidator validator;

	private final JwtService jwtService;

	public UserController(UserProcessManager processManager, UserValidator validator, JwtService jwtService) {
		this.processManager = processManager;
		this.validator = validator;
		this.jwtService = jwtService;
	}

	@PostMapping("/sign-in")
	public ResponseEntity<ResponseDto> signIn(@RequestBody UserRequestDto body) {
		UserSignInCommand command = UserSignInCommand.builder()
			.userName(body.userName())
			.password(body.password())
			.build();
		processManager.manage(command, UserAction.SIGN_IN);
		ResponseDto dto = ResponseDto.builder()
			.data(Map.of("message", "User Authenticated", "token", jwtService.generateToken(body.userName())))
			.build();
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}

	@PostMapping("/sign-up")
	public ResponseEntity<ResponseDto> signUp(@RequestBody UserRequestDto body) {
		validator.validate(body);
		UserSignUpCommand command = UserSignUpCommand.builder()
			.userName(body.userName())
			.password(body.password())
			.firstName(body.firstName())
			.lastName(body.lastName())
			.email(body.email())
			.build();
		processManager.manage(command, UserAction.SIGN_UP);
		ResponseDto dto = ResponseDto.builder()
			.data(Map.of("message", "User Created", "token", jwtService.generateToken(body.userName())))
			.build();
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}

	// TODO: Add Unit tests for Valid JWT
	@PostMapping("/email/verify")
	public ResponseEntity<String> verify(@RequestBody String email) {
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
