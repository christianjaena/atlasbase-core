package com.atlasbase.atlasbase_core.infrastructure.configuration;

import com.atlasbase.atlasbase_core.domain.repository.UserRepository;
import com.atlasbase.atlasbase_core.infrastructure.persistence.adapter.UserJpaRepositoryImpl;
import com.atlasbase.atlasbase_core.infrastructure.persistence.jpa.UserJpaRepository;
import com.atlasbase.atlasbase_core.infrastructure.persistence.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public UserRepository userRepository(UserJpaRepository repository) {
        return new UserJpaRepositoryImpl(repository, new UserMapper());
    }
}
