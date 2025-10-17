package com.atlasbase.atlasbase_core.interfaces.rest.controller;

import com.atlasbase.atlasbase_core.application.UserProcessManager;
import com.atlasbase.atlasbase_core.application.constants.UserActions;
import com.atlasbase.atlasbase_core.domain.model.User;
import com.atlasbase.atlasbase_core.interfaces.rest.dto.UserRequest;
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

    private final UserProcessManager userProcessManager;

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody UserRequest body) {
        userProcessManager.manage(body, UserActions.SIGN_IN);
        return ResponseEntity.status(HttpStatus.OK).body("Authenticated");
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody UserRequest body) {
        userProcessManager.manage(body, UserActions.SIGN_UP);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
