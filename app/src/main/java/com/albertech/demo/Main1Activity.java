package com.albertech.demo;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioGroup;


import com.albertech.demo.func.front.FrontActivity;
import com.albertech.filewatch.api.FileHelper;
import com.albertech.filewatch.core.query.IFileQuery;
import com.albertech.filewatch.api.IFileWatchSubscriber;
import com.albertech.filewatch.api.IFileWatchUnsubscribe;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main1Activity extends AppCompatActivity {

    private static final String TAG = Main1Activity.class.getSimpleName();


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

        @Override
        public void onQueryResult(String path, List<String> list) {
            Log.e(TAG, "Query finish:");
            Log.e(TAG, "Parent path: " + path + "\n");
            for (int i = 0; i < list.size(); i++) {
                Log.e(TAG, "\tFile: " + list.get(i) + "\n");
            }
        }
    };

    private int mFileType;
    private String mPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrontActivity.start(getApplicationContext());
        finish();

        setContentView(R.layout.activity_main1);

        ActivityCompat.requestPermissions(this, PERMISSIONS, 0);

        initView();
        initFileHelper();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnsubscribe.unsubscribe();
    }

    private void initView() {
//        View.OnClickListener l = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (v.getId() == R.id.btn1) {
//                    show(mFileType, mPath);
//                } else if (v.getId() == R.id.btn2) {
//                    refresh(mPath);
//                }
//            }
//        };
//        findViewById(R.id.btn1).setOnClickListener(l);
//        findViewById(R.id.btn2).setOnClickListener(l);

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
        mUnsubscribe = FileHelper.subscribeFileWatch(getApplicationContext(), mSubscriber, SDCARD_PATH);
    }

    

}
