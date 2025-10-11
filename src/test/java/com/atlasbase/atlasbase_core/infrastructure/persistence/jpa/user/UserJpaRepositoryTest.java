package com.atlasbase.atlasbase_core.infrastructure.persistence.jpa.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.liquibase.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class UserJpaRepositoryTest {

    @Autowired
    private UserJpaRepository repository;

    @BeforeEach
    void setup() {
        // Clear DB on every test
        repository.deleteAll();

        UserEntity user = UserEntity.builder()
                .id(UUID.randomUUID())
                .userName("johndoe")
                .email("johndoe@gmail.com")
                .build();
        repository.save(user);
    }

    @Test
    void givenExistingUser_whenFindByUsername_shouldReturnUserEntity() {
        Optional<UserEntity> user = repository.findByUserName("johndoe");

        assertTrue(user.isPresent());
        assertEquals("johndoe", user.get().getUserName());
    }

    @Test
    void givenNonExistingUser_whenFindByUsername_shouldReturnOptionalEmpty() {
        Optional<UserEntity> user = repository.findByUserName("non-existing-users");

        assertTrue(user.isEmpty());
    }
}
