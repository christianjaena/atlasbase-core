package com.atlasbase.atlasbase_core.infrastructure.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtPropertiesTest {

	@Autowired
	private JwtProperties properties;

	@Test
	void shouldBindFromYaml() {
		assertNotNull(properties.secret());
		assertEquals("atlasbase", properties.issuer());
		assertEquals("Bearer ", properties.tokenPrefix());
		assertEquals(900000, properties.expiration());
		assertEquals(604800000, properties.refreshExpiration());
	}

}