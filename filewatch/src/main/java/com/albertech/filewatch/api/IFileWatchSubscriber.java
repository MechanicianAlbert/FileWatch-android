package com.albertech.filewatch.api;


public interface IFileWatchSubscriber {

    void onEvent(int event, String path);

}
