package com.albertech.filewatch.core.scan;

public interface IFileScan {

    void init();

    void scan(String path);

    void release();
}
