package com.atlasbase.atlasbase_core.interfaces.rest.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

// TODO: Unit tests & Integration tests
@Component
public class GoogleTokenVerifier {

	@Value("${google.client-id}")
	private String CLIENT_ID;

	private final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

	public GoogleIdToken.Payload verify(String token) throws Exception {
		var transport = GoogleNetHttpTransport.newTrustedTransport();

		var verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
			.setAudience(Collections.singletonList(CLIENT_ID))
			.build();

		var idToken = verifier.verify(token);

		if (idToken != null) {
			return idToken.getPayload();
		}
		else {
			throw new RuntimeException("Invalid Google ID Token");
		}

	}

}
