package com.albertech.demo.fileobserver.api;


public interface IFileWatch {

    void onEvent(int event, String path);

}
