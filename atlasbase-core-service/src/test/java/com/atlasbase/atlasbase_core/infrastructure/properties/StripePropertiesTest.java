package com.atlasbase.atlasbase_core.infrastructure.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@EnableConfigurationProperties(StripeProperties.class)
class StripePropertiesTest {

	@Autowired
	private StripeProperties properties;

	@Test
	void shouldBindFromYaml() {
		assertNotNull(properties.secretKey());
		assertEquals("https://api.stripe.com", properties.baseUrl());
	}

}