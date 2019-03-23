package com.albertech.demo.fileobserver.core;


public interface IRecursiveFileWatcher {

    void createObservers();

    void startWatching();

    void stopWatching();

}
