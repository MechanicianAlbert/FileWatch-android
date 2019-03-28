package com.albertech.filewatch.api;


import java.util.List;

public interface IFileWatchSubscriber {

    void onEvent(int event, String parentPath, String childPath);

    void onScanResult(String parentPath);

    void onQueryResult(String parentPath, List<String> list);

}
