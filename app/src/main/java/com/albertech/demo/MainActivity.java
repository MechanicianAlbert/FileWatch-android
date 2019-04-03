package com.albertech.demo;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    private final String[] PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private final String SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        HomeActivity.start(getApplicationContext());
//        finish();

        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, PERMISSIONS, 0);

        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initUSB();
            }
        });
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                Uri data = intent.getData();
                if (action != null && data != null) {
                    Log.e("AAA", "Action: " + action + ", Data: " + data);
                    switch (action) {
                        case Intent.ACTION_MEDIA_MOUNTED:
                            Toast.makeText(getApplicationContext(), "Media Mounted", Toast.LENGTH_SHORT).show();
                            String path = data.getPath();
                            Log.e("AAA", path);
                            break;
                        case Intent.ACTION_MEDIA_UNMOUNTED:
                            break;
                        case Intent.ACTION_MEDIA_SCANNER_STARTED:
                            break;
                        case Intent.ACTION_MEDIA_SCANNER_FINISHED:
                            break;
                        case Intent.ACTION_MEDIA_SCANNER_SCAN_FILE:
                            break;
                    }
                }
            }

        }
    };

    private void initUSB() {
        IntentFilter filter = new IntentFilter();

        filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        filter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
        filter.addAction(Intent.ACTION_MEDIA_REMOVED);

        filter.addAction(Intent.ACTION_MEDIA_SCANNER_STARTED);
        filter.addAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        filter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);

        filter.addDataScheme(ContentResolver.SCHEME_FILE);


//        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
//        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);


        getApplicationContext().registerReceiver(mReceiver, filter);

        Log.e("AAA", "USB Mount broadcast registered");
    }


}
