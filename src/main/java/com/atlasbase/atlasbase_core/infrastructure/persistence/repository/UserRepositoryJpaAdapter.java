package com.atlasbase.atlasbase_core.infrastructure.persistence.repository;

import com.atlasbase.atlasbase_core.core.model.User;
import com.atlasbase.atlasbase_core.core.port.UserRepository;
import com.atlasbase.atlasbase_core.infrastructure.persistence.mapper.UserMapper;
import java.util.Optional;

public class UserRepositoryJpaAdapter implements UserRepository {

    private final UserJpaRepository repository;
    private final UserMapper mapper;

    public UserRepositoryJpaAdapter(UserJpaRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return repository.findByUserName(userName).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public void save(User user) { // TODO: Make this User so that Entity never
        repository.save(mapper.toEntity(user));
    }
}
