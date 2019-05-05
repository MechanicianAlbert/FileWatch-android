package com.albertech.filewatch.core.query;

/**
 * 文件类型常量接口
 */
public interface IFileType {

    int DIRECTORY = 0;

    int IMAGE = 1;
    int AUDIO = 2;
    int VIDEO = 3;
    int DOC = 4;
    int ZIP = 5;
    int APK = 6;
    int FILE = 7;

    int UNKNOWN = 8;
}