package com.atlasbase.atlasbase_core.infrastructure.persistence.adapter;

import com.atlasbase.atlasbase_core.domain.model.User;
import com.atlasbase.atlasbase_core.domain.repository.UserRepository;
import com.atlasbase.atlasbase_core.infrastructure.persistence.jpa.UserJpaRepository;
import com.atlasbase.atlasbase_core.infrastructure.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserJpaRepositoryImpl implements UserRepository {

    private final UserJpaRepository repository;
    private final UserMapper mapper;

    @Override
    public Optional<User> findByUserName(String userName) {
        return repository.findByUserName(userName).map(mapper::toDomain);
    }
}
