package com.albertech.demo.util;

import android.app.Application;


public class GlobalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {
        Res.setApplication(this);
    }
}
