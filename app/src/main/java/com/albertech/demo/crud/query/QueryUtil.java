package com.albertech.demo.crud.query;

import android.database.Cursor;
import android.provider.MediaStore;

import com.albertech.demo.R;
import com.albertech.demo.base.bean.BaseFileBean;
import com.albertech.filewatch.api.IFileConstant;

import java.io.File;
import java.util.Arrays;


public class QueryUtil implements IFileConstant {


    /**
     * Help to parse general info from cursor, and fill into bean
     * <p>
     * Remember: do not close cursor here, cursor would still be used
     *
     * @param cursor cursor from MediaProvider
     * @param bean   concrete bean extends BaseFileBean
     * @return see input param bean
     */
    public static <Bean extends BaseFileBean> Bean parseCursorToBaseFileBean(Cursor cursor, Bean bean) {
        bean.path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
        bean.file = new File(bean.path);
        bean.isDirectory = bean.file.isDirectory();
        bean.name = bean.file.getName();
        bean.size = bean.file.length();
        bean.date = bean.file.lastModified();
        bean.parent = bean.file.getParent();
        bean.isHidden = bean.file.isHidden();
        boolean hasSuffix = bean.name.contains(".");
        if (hasSuffix) {
            bean.suffix = bean.name.substring(bean.name.lastIndexOf("."));
        } else {
            bean.suffix = "";
        }
        int type = UNKNOWN;
        if (!(cursor.getColumnIndex(MediaStore.Files.FileColumns.MEDIA_TYPE) < 0)) {
            type = cursor.getInt(cursor.getColumnIndex(MediaStore.Files.FileColumns.MEDIA_TYPE));
        }
        if (bean.isDirectory) {
            bean.type = DIRECTORY;
            bean.icon = R.drawable.ic_type_folder;
        } else if (type == MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE) {
            bean.type = IMAGE;
            bean.icon = R.drawable.ic_type_image;
        } else if (type == MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO) {
            bean.type = AUDIO;
            bean.icon = R.drawable.ic_type_audio;
        } else if (type == MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO) {
            bean.type = VIDEO;
            bean.icon = R.drawable.ic_type_video;
        } else if (Arrays.asList(IFileConstant.DOC_SUFFIX).contains(bean.suffix)) {
            bean.type = DOC;
            bean.icon = R.drawable.ic_type_doc;
        } else if (Arrays.asList(IFileConstant.APK_SUFFIX).contains(bean.suffix)) {
            bean.type = APK;
            bean.icon = R.drawable.ic_type_apk;
        } else if (Arrays.asList(IFileConstant.ZIP_SUFFIX).contains(bean.suffix)) {
            bean.type = ZIP;
            bean.icon = R.drawable.ic_type_zip;
        } else {
            bean.type = UNKNOWN;
            bean.icon = R.drawable.ic_type_file;
        }
        return bean;
    }
}
