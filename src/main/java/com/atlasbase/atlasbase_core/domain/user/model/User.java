package com.atlasbase.atlasbase_core.domain.user.model;


import com.atlasbase.atlasbase_core.domain.common.Metadata;

import java.util.UUID;

public class User {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private Metadata metadata;
}
