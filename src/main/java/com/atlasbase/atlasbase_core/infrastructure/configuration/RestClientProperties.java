package com.atlasbase.atlasbase_core.infrastructure.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "rest-client")
public record RestClientProperties(Pool pool, Timeout timeout) {

    public record Pool(int maxTotal, int defaultMaxPerRoute) { }

    public record Timeout(Duration connectTimeout,
                          Duration responseTimeout,
                          Duration connectionRequestTimeout) { }
}
