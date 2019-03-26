package com.albertech.demo.filewatch.core;

public interface IFileWatch {

    void onFileEvent(int event, String fullPath);
}
