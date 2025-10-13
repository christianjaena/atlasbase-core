package com.atlasbase.atlasbase_core.interfaces.rest.controller;

import com.atlasbase.atlasbase_core.interfaces.rest.dto.SignInRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(body.email(), body.password());

            authenticationManager.authenticate(authenticationToken);

            return ResponseEntity.ok("Login successful!");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
