package com.atlasbase.atlasbase_core.infrastructure.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class RestClientPropertiesTest {

	@Autowired
	private RestClientProperties restClientProperties;

	@Test
	void thenBindPropertiesFromYaml() {
		RestClientProperties.Pool pool = restClientProperties.pool();
		RestClientProperties.Timeout timeout = restClientProperties.timeout();

		assertThat(pool.maxTotal()).isEqualTo(150);
		assertThat(pool.defaultMaxPerRoute()).isEqualTo(50);

		assertThat(timeout.connectTimeout()).isEqualTo(Duration.ofSeconds(2));
		assertThat(timeout.responseTimeout()).isEqualTo(Duration.ofSeconds(3));
		assertThat(timeout.connectionRequestTimeout()).isEqualTo(Duration.ofSeconds(1));
	}

}