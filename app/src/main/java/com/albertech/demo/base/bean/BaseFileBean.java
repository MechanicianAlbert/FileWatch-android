package com.albertech.demo.base.bean;

import com.albertech.filehelper.core.query.IFileType;

import java.io.File;

public class BaseFileBean implements IFileType {

    public String path;
    public File file;
    public boolean isDirectory;
    public boolean isHidden;
    public String name;
    public long size;
    public long date;
    public String parent;
    public String suffix;
    public int type;
    public int icon;



    public final boolean isDirectory() {
        return isDirectory;
    }

    public final boolean isHidden() {
        return isHidden;
    }
}
