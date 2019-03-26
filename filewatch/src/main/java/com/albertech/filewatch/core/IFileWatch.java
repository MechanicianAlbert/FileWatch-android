package com.albertech.filewatch.core;



public interface IFileWatch {

    void onFileEvent(int event, String fullPath);
}
