package com.albertech.filewatch.core;



public interface IGlobalFileWatch {

    void startWatching();

    void resumeWatching();

    void pauseWatching();

    void stopWatching();

}
