package com.albertech.demo.filewatch.practice;

import android.os.Environment;
import android.os.FileObserver;

public interface FileWatchDefaultConfig {

    String PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    int EVENTS = FileObserver.CREATE
            | FileObserver.DELETE;

}
