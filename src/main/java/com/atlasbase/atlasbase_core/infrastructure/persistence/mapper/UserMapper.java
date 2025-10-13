package com.atlasbase.atlasbase_core.infrastructure.persistence.mapper;

import com.atlasbase.atlasbase_core.domain.model.User;
import com.atlasbase.atlasbase_core.infrastructure.persistence.entity.UserEntity;

public class UserMapper implements Mapper<User, UserEntity> {

    @Override
    public UserEntity toEntity(User user) {
        if (user == null) return null;

        return new UserEntity(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUserName(),
                user.getPassword(),
                user.getMetadata()
        );
    }

    @Override
    public User toDomain(UserEntity user) {
        if (user == null) return null;

        return new User(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUserName(),
                user.getPassword(),
                user.getMetadata()
        );
    }
}
