package com.albertech.demo.filewatch.core;


public interface IGlobalFileWatch {

    void startWatching();

    void resumeWatching();

    void pauseWatching();

    void stopWatching();

}
