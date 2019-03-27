package com.albertech.filewatch.watch;



public interface IFileWatch {

    void onEvent(int event, String fullPath);
}
