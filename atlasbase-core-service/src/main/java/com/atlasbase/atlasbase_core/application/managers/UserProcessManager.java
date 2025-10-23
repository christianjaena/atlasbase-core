package com.atlasbase.atlasbase_core.application.managers;

import com.atlasbase.atlasbase_core.application.constants.UserAction;
import com.atlasbase.atlasbase_core.application.interfaces.ProcessManager;
import com.atlasbase.atlasbase_core.application.interfaces.Processor;
import com.atlasbase.atlasbase_core.application.processors.UserSignInProcessor;
import com.atlasbase.atlasbase_core.application.processors.UserSignUpProcessor;
import com.atlasbase.atlasbase_core.application.dto.UserRequest;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserProcessManager implements ProcessManager<UserRequest, UserAction> {

	private final Map<UserAction, Processor<UserRequest>> processorMap;

	public UserProcessManager(UserSignInProcessor userSignInProcessor, UserSignUpProcessor userSignUpProcessor) {
		this.processorMap = Map.of(UserAction.SIGN_IN, userSignInProcessor, UserAction.SIGN_UP, userSignUpProcessor);
	}

	@Override
	public void manage(UserRequest payload, UserAction action) {
		processorMap.get(action).process(payload);
	}

}
