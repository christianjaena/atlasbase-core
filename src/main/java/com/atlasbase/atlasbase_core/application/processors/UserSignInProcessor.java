package com.atlasbase.atlasbase_core.application.processors;

import com.atlasbase.atlasbase_core.application.interfaces.Processor;
import com.atlasbase.atlasbase_core.interfaces.rest.dto.UserRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserSignInProcessor implements Processor<UserRequest> {

    private final AuthenticationManager authenticationManager;

    public UserSignInProcessor(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void process(UserRequest request) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(),
                        request.password())
        );

        if (!authenticate.isAuthenticated()) {
            throw new BadCredentialsException("Invalid credentials");
        }
    }
}
