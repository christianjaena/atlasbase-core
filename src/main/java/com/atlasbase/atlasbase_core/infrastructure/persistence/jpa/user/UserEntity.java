package com.atlasbase.atlasbase_core.infrastructure.persistence.jpa.user;

import com.atlasbase.atlasbase_core.domain.common.Metadata;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {

    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;

    @Embedded
    private Metadata metadata;
}
