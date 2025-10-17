package com.atlasbase.atlasbase_core.application.processors;

import com.atlasbase.atlasbase_core.application.interfaces.Processor;
import com.atlasbase.atlasbase_core.domain.repository.UserRepository;
import com.atlasbase.atlasbase_core.interfaces.rest.dto.UserRequest;
import org.springframework.stereotype.Component;

@Component
public class UserSignUpProcessor implements Processor<UserRequest> {

    private final UserRepository repository;

    public UserSignUpProcessor(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void process(UserRequest type) {

//        repository.findByEmail(type.email()).ifPresent(user -> {
//            throw new IllegalArgumentException("User already exists");
//        });
//
//        repository.findByUserName(type.userName()).ifPresent(user -> {
//            throw new IllegalArgumentException("User already exists");
//        });
//
//        repository.save();
//
    }
}
