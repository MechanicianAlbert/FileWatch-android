package com.albertech.demo.util;

import android.app.Application;

import com.albertech.filehelper.api.FileHelper;


public class GlobalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {
        Res.setApplication(this);
        FileHelper.init(this);
    }

}
