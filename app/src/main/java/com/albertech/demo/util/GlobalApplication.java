package com.albertech.demo.util;

import android.app.Application;

import com.albertech.demo.util.query.FileQueryHelper;

public class GlobalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {
        Res.setApplication(this);
        FileQueryHelper.getInstance();
    }
}
