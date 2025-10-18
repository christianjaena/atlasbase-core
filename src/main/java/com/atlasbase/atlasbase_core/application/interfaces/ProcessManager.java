package com.atlasbase.atlasbase_core.application.interfaces;

public interface ProcessManager<T, E> {
    void manage(T type, E action);
}
