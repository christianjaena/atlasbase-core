package com.atlasbase.atlasbase_core.application.interfaces;

public interface ProcessManager<C, A> {

	void manage(C command, A action);

}
