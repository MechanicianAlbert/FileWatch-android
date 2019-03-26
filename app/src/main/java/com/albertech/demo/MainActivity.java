package com.albertech.demo;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.albertech.demo.filewatch.api.FileWatchHelper;
import com.albertech.demo.filewatch.practice.GlobalFileWatchService;
import com.albertech.demo.filewatch.api.IFileWatch;


public class MainActivity extends AppCompatActivity implements IFileWatch {

    private String[] PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    private final ServiceConnection CONNECTION = new ServiceConnection() {

        GlobalFileWatchService.FileWatchBinder mBinder;


        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service instanceof GlobalFileWatchService.FileWatchBinder) {
                mBinder = (GlobalFileWatchService.FileWatchBinder) service;
                mBinder.registerFileSystemWatch(MainActivity.this, PATH);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (mBinder != null) {
                mBinder.unregisterFileSystemWatch(MainActivity.this);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, PERMISSIONS, 0);

        bindService(new Intent(getApplicationContext(), GlobalFileWatchService.class), CONNECTION, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(CONNECTION);
    }

    @Override
    public void onEvent(int event, String path) {
        Log.e("AAA", FileWatchHelper.name(event) + ": " + path);
    }
}
