package com.atlasbase.atlasbase_core.application;

import com.atlasbase.atlasbase_core.application.constants.UserActions;
import com.atlasbase.atlasbase_core.application.interfaces.ProcessManager;
import com.atlasbase.atlasbase_core.application.interfaces.Processor;
import com.atlasbase.atlasbase_core.application.processors.UserSignInProcessor;
import com.atlasbase.atlasbase_core.application.processors.UserSignUpProcessor;
import com.atlasbase.atlasbase_core.interfaces.rest.dto.UserRequest;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserProcessManager implements ProcessManager<UserRequest> {

    private final Map<String, Processor<UserRequest>> processorMap;

    public UserProcessManager(UserSignInProcessor userSignInProcessor,
                             UserSignUpProcessor userSignUpProcessor) {
        this.processorMap = Map.of(
                UserActions.SIGN_IN, userSignInProcessor,
                UserActions.SIGN_UP, userSignUpProcessor
        );
    }

    @Override
    public void manage(UserRequest payload, String action) {
        processorMap.get(action).process(payload);
    }
}
