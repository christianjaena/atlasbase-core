package com.atlasbase.atlasbase_core.interfaces.rest.controller;

import com.atlasbase.atlasbase_core.interfaces.rest.dto.SignInRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody SignInRequest body) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(body.email(),
                        body.password())
        );

        return authenticate.isAuthenticated()
                ? ResponseEntity.ok("Authenticated")
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthenticated");
    }
}
