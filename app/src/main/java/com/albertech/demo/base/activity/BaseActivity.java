package com.albertech.demo.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutRese());
        init();
    }

    private void init() {
        initArgs(getIntent());
        initPermission();
        initView();
        initListener();
        initData();
    }

    protected void initArgs(Intent intent) {

    }

    protected void initPermission() {

    }

    protected void initView() {

    }

    protected void initListener() {

    }

    protected void initData() {

    }


    protected abstract int layoutRese();

}
