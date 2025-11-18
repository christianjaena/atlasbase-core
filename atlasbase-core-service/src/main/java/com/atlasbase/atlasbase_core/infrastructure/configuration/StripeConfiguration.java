package com.atlasbase.atlasbase_core.infrastructure.configuration;

import com.atlasbase.atlasbase_core.infrastructure.properties.StripeProperties;
import com.stripe.StripeClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StripeProperties.class)
public class StripeConfiguration {

	@Bean
	public StripeClient stripeClient(StripeProperties properties) {
		return new StripeClient(properties.secretKey());
	}

}
