package com.atlasbase.atlasbase_core;

import com.atlasbase.atlasbase_core.domain.common.Metadata;
import com.atlasbase.atlasbase_core.domain.user.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestFixtures {

    public static User userMock() {
        Metadata metadata = new Metadata();
        metadata.setCreateDate(Instant.now());
        metadata.setUpdateDate(Instant.now());
        metadata.setCreatedBy("TEST");
        metadata.setUpdatedBy("TEST");

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("testuser@gmail.com");
        user.setUserName("test-user");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setMetadata(metadata);
        user.setPassword("password");
        return user;
    }
}
