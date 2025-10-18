package com.atlasbase.atlasbase_core.infrastructure.configuration;

import com.atlasbase.atlasbase_core.core.port.UserRepository;
import com.atlasbase.atlasbase_core.infrastructure.persistence.repository.UserRepositoryJpaAdapter;
import com.atlasbase.atlasbase_core.infrastructure.persistence.repository.UserJpaRepository;
import com.atlasbase.atlasbase_core.infrastructure.persistence.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfiguration {

    @Bean
    public UserRepository userRepository(UserJpaRepository repository, UserMapper mapper) {
        return new UserRepositoryJpaAdapter(repository, mapper);
    }
}
