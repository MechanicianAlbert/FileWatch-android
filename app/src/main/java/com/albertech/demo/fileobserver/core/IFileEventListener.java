package com.albertech.demo.fileobserver.core;

public interface IFileEventListener {

    void onFileEvent(int event, String fullPath);
}
