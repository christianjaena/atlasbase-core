package com.atlasbase.atlasbase_core.infrastructure.persistence.mapper;

import com.atlasbase.atlasbase_core.domain.model.User;
import com.atlasbase.atlasbase_core.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

// TODO: Unit tests

@Component
public class UserMapper {

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
