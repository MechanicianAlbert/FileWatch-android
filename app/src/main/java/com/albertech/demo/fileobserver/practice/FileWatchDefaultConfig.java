package com.albertech.demo.fileobserver.practice;

import android.os.Environment;
import android.os.FileObserver;

import java.io.File;

public interface FileWatchDefaultConfig {

    String PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    int EVENTS = FileObserver.ALL_EVENTS
            & ~FileObserver.ACCESS
            & ~FileObserver.ATTRIB
            & ~FileObserver.CLOSE_NOWRITE
            & ~FileObserver.DELETE_SELF
            & ~FileObserver.MOVE_SELF;
}
