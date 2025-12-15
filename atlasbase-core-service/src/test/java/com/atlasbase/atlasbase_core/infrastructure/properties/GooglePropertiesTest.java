package com.atlasbase.atlasbase_core.infrastructure.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EnableConfigurationProperties(GoogleProperties.class)
class GooglePropertiesTest {

	@Autowired
	private GoogleProperties properties;

	@Test
	void shouldBindFromYaml() {
		assertNotNull(properties.clientId());
	}

}