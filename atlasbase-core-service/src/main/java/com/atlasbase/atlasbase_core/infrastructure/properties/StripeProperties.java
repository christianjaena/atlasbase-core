package com.atlasbase.atlasbase_core.infrastructure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "stripe")
public record StripeProperties(String baseUrl, String secretKey) {
}
