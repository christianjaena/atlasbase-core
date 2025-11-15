package com.atlasbase.atlasbase_core.interfaces.rest.controller;

import com.atlasbase.atlasbase_core.application.services.JwtService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// TODO: Unit tests & Integration tests
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final JwtService jwtService;

	private final GoogleTokenVerifier googleTokenVerifier;

	public AuthController(JwtService jwtService, GoogleTokenVerifier googleTokenVerifier) {
		this.jwtService = jwtService;
		this.googleTokenVerifier = googleTokenVerifier;
	}

	@PostMapping("/google")
	public ResponseEntity<?> googleOAuthLogin(@RequestBody Map<String, String> body) throws Exception {
		String idTokenString = body.get("token");
		var payload = googleTokenVerifier.verify(idTokenString);

		String email = payload.getEmail();
		String name = payload.get("name").toString();
		var jsonResponse = Map.of("token", jwtService.generateToken("google"), "email", email, "name", name);
		return ResponseEntity.ok().body(jsonResponse);
	}

}
