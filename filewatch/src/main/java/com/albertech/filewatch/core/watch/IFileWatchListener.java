package com.albertech.filewatch.core.watch;



public interface IFileWatchListener {

    void onEvent(int event, String parentPath, String fullPath);
}
