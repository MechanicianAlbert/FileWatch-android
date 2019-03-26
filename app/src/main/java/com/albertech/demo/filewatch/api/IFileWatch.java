package com.albertech.demo.filewatch.api;


public interface IFileWatch {

    void onEvent(int event, String path);

}
