package com.albertech.demo.base.bean;

import com.albertech.filewatch.core.query.IFileType;

public class BaseFileBean implements IFileType {

    public String path;

    public String name;

    public long size;

    public long date;

    public long parent;

    public int type;



    public final boolean isDirectory() {
        return type == DIRECTORY;
    }
}
