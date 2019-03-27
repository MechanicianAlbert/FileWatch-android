package com.albertech.filewatch.watch;



public interface IFileWatchBus {

    void startWatching();

    void resumeWatching();

    void pauseWatching();

    void stopWatching();

}
