package com.albertech.filewatch.api;

import java.util.List;


public interface IFileQuery {

    int FILE = 0;
    int IMAGE = 1;
    int AUDIO = 2;
    int VIDEO = 3;
    int DOC = 4;
    int ZIP = 5;
    int APK = 6;


    List<String> queryFileByTypeAndPath(int type, String path);
}
