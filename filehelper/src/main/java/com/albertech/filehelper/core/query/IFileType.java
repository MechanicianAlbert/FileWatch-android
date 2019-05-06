package com.albertech.filehelper.core.query;

/**
 * 文件类型常量接口
 */
public interface IFileType {

    /**
     * 目录
     */
    int DIRECTORY = 0;
    /**
     * 图片
     */
    int IMAGE = 1;
    /**
     * 音频
     */
    int AUDIO = 2;
    /**
     * 视频
     */
    int VIDEO = 3;
    /**
     * 文档
     */
    int DOC = 4;
    /**
     * 压缩文件
     */
    int ZIP = 5;
    /**
     * 安装文件
     */
    int APK = 6;
    /**
     * 所有文件
     */
    int FILE = 7;
    /**
     * 其他未知类型
     */
    int UNKNOWN = 8;
}
