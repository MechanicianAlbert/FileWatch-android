package com.albertech.demo.filewatch.core;

public interface IFileEventListener {

    void onFileEvent(int event, String fullPath);
}
