package com.atlasbase.atlasbase_core.interfaces.web.controller;

import com.atlasbase.atlasbase_core.interfaces.web.dto.SignInRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody SignInRequest body) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Invalid credentials");
    }
}
