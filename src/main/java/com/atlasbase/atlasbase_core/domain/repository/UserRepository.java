package com.atlasbase.atlasbase_core.domain.repository;

import com.atlasbase.atlasbase_core.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUserName(String userName);
}