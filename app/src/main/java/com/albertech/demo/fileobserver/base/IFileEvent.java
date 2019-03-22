package com.albertech.demo.fileobserver.base;

public interface IFileEvent {

    void onFileEvent(int event, String path);
}
