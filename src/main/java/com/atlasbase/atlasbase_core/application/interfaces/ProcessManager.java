package com.atlasbase.atlasbase_core.application.interfaces;

public interface ProcessManager<T> {
    void manage(T type, String action);
}
