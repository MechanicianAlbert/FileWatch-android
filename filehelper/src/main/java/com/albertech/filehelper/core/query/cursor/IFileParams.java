package com.albertech.filehelper.core.query.cursor;

import android.net.Uri;
import android.provider.MediaStore;

import com.albertech.filehelper.core.IConstant;

/**
 * 文件查询常量接口
 */
public interface IFileParams extends IConstant {

    /**
     * 系统媒体库文件列表资源标识符
     */
    Uri URI_FILES = MediaStore.Files.getContentUri("external");

    /**
     * 系统文件数据库路径字段名
     */
    String PATH_COLUMN_NAME = MediaStore.Files.FileColumns.DATA;
    /**
     * 系统文件数据库文件类型字段名
     */
    String COLUMN_NAME_MEDIA_TYPE = MediaStore.Files.FileColumns.MEDIA_TYPE;

    /**
     * 文档文件后缀数组
     */
    String[] DOC_SUFFIX = new String[]{
            ".pdf",
            ".doc",
            ".docx",
            ".xls",
            ".xlsx",
            ".ppt",
            ".pptx",
            ".txt"
    };

    /**
     * 安装文件后缀数组
     */
    String[] APK_SUFFIX = new String[]{
            ".apk"
    };

    /**
     * 压缩文件后缀数组
     */
    String[] ZIP_SUFFIX = new String[]{
            ".zip",
            ".rar",
            ".tar",
            ".7z"
    };

}
