package com.albertech.demo.filewatch.core;


public interface IRecursiveFileWatcher {

    void createObservers();

    void startWatching();

    void stopWatching();

}
