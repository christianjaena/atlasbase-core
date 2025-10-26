package com.atlasbase.atlasbase_core.application.managers;

import com.atlasbase.atlasbase_core.application.commands.BaseCommand;
import com.atlasbase.atlasbase_core.application.constants.UserAction;
import com.atlasbase.atlasbase_core.application.interfaces.ProcessManager;
import com.atlasbase.atlasbase_core.application.interfaces.Processor;
import com.atlasbase.atlasbase_core.application.processors.UserSignInProcessor;
import com.atlasbase.atlasbase_core.application.processors.UserSignUpProcessor;
import com.atlasbase.atlasbase_core.application.dto.UserRequestDto;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserProcessManager implements ProcessManager<BaseCommand, UserAction> {

	private final Map<UserAction, Processor<? extends BaseCommand>> processorMap;

	public UserProcessManager(UserSignInProcessor userSignInProcessor, UserSignUpProcessor userSignUpProcessor) {
		this.processorMap = Map.of(UserAction.SIGN_IN, userSignInProcessor, UserAction.SIGN_UP, userSignUpProcessor);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void manage(BaseCommand command, UserAction action) {
		Processor<BaseCommand> processor = (Processor<BaseCommand>) processorMap.get(action);
		processor.process(command);
	}

}
