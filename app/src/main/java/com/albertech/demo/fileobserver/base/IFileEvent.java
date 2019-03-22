package com.albertech.demo.fileobserver.base;

public interface IFileEvent {

    void onFileEvent(String selfPath, int event, String eventPath);
}
