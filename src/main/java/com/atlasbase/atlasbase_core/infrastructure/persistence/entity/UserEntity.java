package com.atlasbase.atlasbase_core.infrastructure.persistence.entity;

import com.atlasbase.atlasbase_core.domain.model.Metadata;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
