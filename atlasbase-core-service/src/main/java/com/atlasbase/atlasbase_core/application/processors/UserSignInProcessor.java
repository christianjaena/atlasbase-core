package com.atlasbase.atlasbase_core.application.processors;

import com.atlasbase.atlasbase_core.application.commands.UserSignInCommand;
import com.atlasbase.atlasbase_core.application.interfaces.Processor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserSignInProcessor implements Processor<UserSignInCommand> {

	private final AuthenticationManager authenticationManager;

	public UserSignInProcessor(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public void process(UserSignInCommand command) {
		Authentication authenticate = authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(command.getUserName(), command.getPassword()));

		if (!authenticate.isAuthenticated()) {
			throw new BadCredentialsException("Invalid credentials");
		}
	}

}
