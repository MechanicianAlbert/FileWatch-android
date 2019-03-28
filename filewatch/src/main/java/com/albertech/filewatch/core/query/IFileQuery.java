package com.albertech.filewatch.core.query;


public interface IFileQuery {

    int FILE = 0;
    int IMAGE = 1;
    int AUDIO = 2;
    int VIDEO = 3;
    int DOC = 4;
    int ZIP = 5;
    int APK = 6;


    void queryFileByTypeAndPath(int type, String path, IFileQureyListener listener);
}
