package com.albertech.demo.filewatch.api;


public interface IFileWatchSubscriber {

    void onEvent(int event, String path);

}
