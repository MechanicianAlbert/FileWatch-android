package com.albertech.demo.fileobserver.practice;

import android.os.Environment;
import android.os.FileObserver;

import java.io.File;

public interface FileWatchConstants {

    String PATH = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "AAAA1").getAbsolutePath();
    int EVENTS = FileObserver.CREATE
            | FileObserver.DELETE_SELF
            | FileObserver.DELETE
            | FileObserver.MOVE_SELF
            | FileObserver.MOVED_FROM
            | FileObserver.MOVED_TO
            | FileObserver.MODIFY;
}