package com.albertech.filewatch.api;


public interface IFileWatchSubscriber {

    void onEvent(int event, String parentPath, String childPath);

    void onScanResult(String parentPath);

}
