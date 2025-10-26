package com.atlasbase.atlasbase_core.application.interfaces;

import com.atlasbase.atlasbase_core.application.commands.BaseCommand;

public interface Processor<C extends BaseCommand> {

	void process(C command);

}
