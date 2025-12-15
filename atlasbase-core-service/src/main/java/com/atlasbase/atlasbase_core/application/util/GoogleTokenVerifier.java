package com.atlasbase.atlasbase_core.application.util;

import com.atlasbase.atlasbase_core.infrastructure.properties.GoogleProperties;
import com.atlasbase.atlasbase_core.infrastructure.properties.StripeProperties;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;

// TODO: Unit tests & Integration tests
@Component
@EnableConfigurationProperties(GoogleProperties.class)
public class GoogleTokenVerifier {

	private final GoogleProperties googleProperties;

	private final JsonFactory jsonFactory;

	public GoogleTokenVerifier(GoogleProperties googleProperties) {
		this.googleProperties = googleProperties;
		this.jsonFactory = GsonFactory.getDefaultInstance();
	}

	public GoogleIdToken.Payload verify(String token) throws Exception {
		var transport = GoogleNetHttpTransport.newTrustedTransport();

		var verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
			.setAudience(Collections.singletonList(googleProperties.clientId()))
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
