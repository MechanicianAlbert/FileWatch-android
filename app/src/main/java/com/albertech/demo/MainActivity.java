package com.albertech.demo;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.albertech.filewatch.api.FileWatch;
import com.albertech.filewatch.api.IFileWatchSubscriber;
import com.albertech.filewatch.api.IFileWatchUnsubscribe;


public class MainActivity extends AppCompatActivity implements IFileWatchSubscriber {

    private String[] PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    private IFileWatchUnsubscribe mUnsubscribe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, PERMISSIONS, 0);

        mUnsubscribe = FileWatch.subscribeFileWatch(getApplicationContext(), this, PATH);

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUnsubscribe != null) {
                    mUnsubscribe.unsubscribe();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnsubscribe != null) {
            mUnsubscribe.unsubscribe();
        }
    }

    @Override
    public void onEvent(int event, String path) {
        Log.e("AAA", FileWatch.name(event) + ": " + path);
    }
}
