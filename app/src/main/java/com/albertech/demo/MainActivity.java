package com.albertech.demo;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.albertech.demo.container.home.HomeActivity;
import com.albertech.filewatch.api.FileHelper;
import com.albertech.filewatch.core.query.IFileQuery;
import com.albertech.filewatch.api.IFileWatchSubscriber;
import com.albertech.filewatch.api.IFileWatchUnsubscribe;
import com.albertech.filewatch.core.scan.FileScanner;
import com.albertech.filewatch.core.scan.IFileScan;
import com.albertech.filewatch.core.scan.IFileScanListener;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    private final String[] PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private final String SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    private final String AAA_PATH = SDCARD_PATH + File.separator + "AAA" + File.separator;
    private final String DOWNLOAD_PATH = SDCARD_PATH + File.separator + "Download" + File.separator;

    private final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();


    private IFileWatchUnsubscribe mUnsubscribe;
    private IFileWatchSubscriber mSubscriber = new IFileWatchSubscriber() {
        @Override
        public void onEvent(int event, String parentPath, String childPath) {
            Log.e(TAG, FileHelper.fileOperationName(event) + ": " + childPath);
        }

        @Override
        public void onScanResult(String path) {
            Log.e(TAG, "Scan finish: " + path);
        }

    };

    private int mFileType;
    private String mPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        HomeActivity.start(getApplicationContext());
//        finish();

        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, PERMISSIONS, 0);

        initView();
        initFileHelper();

        initUSB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnsubscribe != null) {
            mUnsubscribe.unsubscribe();
        }
    }

    private void initView() {

        RadioGroup.OnCheckedChangeListener cl = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_image) {
                    mFileType = IFileQuery.IMAGE;
                } else if (checkedId == R.id.rb_audio) {
                    mFileType = IFileQuery.AUDIO;
                } else if (checkedId == R.id.rb_video) {
                    mFileType = IFileQuery.VIDEO;
                } else if (checkedId == R.id.rb_doc) {
                    mFileType = IFileQuery.DOC;
                } else if (checkedId == R.id.rb_zip) {
                    mFileType = IFileQuery.ZIP;
                } else if (checkedId == R.id.rb_apk) {
                    mFileType = IFileQuery.APK;
                } else if (checkedId == R.id.rb_file) {
                    mFileType = IFileQuery.FILE;
                } else if (checkedId == R.id.rb_sd) {
                    mPath = SDCARD_PATH;
                } else if (checkedId == R.id.rb_aaa) {
                    mPath = AAA_PATH;
                } else if (checkedId == R.id.rb_download) {
                    mPath = DOWNLOAD_PATH;
                }
            }
        };
        RadioGroup rg1 = findViewById(R.id.rg_type);
        RadioGroup rg2 = findViewById(R.id.rg_path);
        rg1.setOnCheckedChangeListener(cl);
        rg2.setOnCheckedChangeListener(cl);
    }

    private void initFileHelper() {
//        mUnsubscribe = FileHelper.subscribeFileWatch(getApplicationContext(), mSubscriber, SDCARD_PATH);
    }


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if (intent.getAction().equals(Intent.ACTION_MEDIA_MOUNTED)) {
                    Toast.makeText(getApplicationContext(), "Media Mounted", Toast.LENGTH_SHORT).show();
                    String path = intent.getData().getPath();
                    Log.e("AAA", path);
                } else if (intent.getAction().equals(Intent.ACTION_MEDIA_UNMOUNTED)) {

                }

            }

        }
    };

    private void initUSB() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        intentFilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
        intentFilter.addDataScheme(ContentResolver.SCHEME_FILE);
        registerReceiver(mReceiver, intentFilter);
    }


}
