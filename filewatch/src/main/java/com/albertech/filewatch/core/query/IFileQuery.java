package com.albertech.filewatch.core.query;


public interface IFileQuery {

    int IMAGE = 1;
    int AUDIO = 2;
    int VIDEO = 3;
    int DOC = 4;
    int ZIP = 5;
    int APK = 6;
    int FILE = 7;


    void queryFileByTypeAndPath(int type, String path, IFileQureyListener listener);

    void queryFileByTypeAndPath(int type, String path, boolean recursive, IFileQureyListener listener);
}
