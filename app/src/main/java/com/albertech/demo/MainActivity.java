package com.albertech.demo;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.albertech.demo.fileobserver.api.FileWatchHelper;
import com.albertech.demo.fileobserver.practice.FileWatchService;
import com.albertech.demo.fileobserver.practice.GLobalFileSystemObserver;
import com.albertech.demo.fileobserver.api.IFileWatch;
import com.albertech.demo.fileobserver.base.IRecursiveFileObserver;


public class MainActivity extends AppCompatActivity implements IFileWatch {

    private String[] PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private final ServiceConnection CONNECTION = new ServiceConnection() {

        FileWatchService.FileWatchBinder mBinder;


        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service instanceof FileWatchService.FileWatchBinder) {
                mBinder = (FileWatchService.FileWatchBinder) service;
                mBinder.registerFileSystemWatch(MainActivity.this);
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

        GLobalFileSystemObserver.getInstance().registerFileSystemWatch(this);
//        bindService(new Intent(getApplicationContext(), FileWatchService.class), CONNECTION, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GLobalFileSystemObserver.getInstance().unregisterFileSystemWatch(this);
//        unbindService(CONNECTION);
    }

    @Override
    public void onEvent(int event, String path) {
        Log.e("AAA", FileWatchHelper.name(event) + ": " + path);
    }
}
