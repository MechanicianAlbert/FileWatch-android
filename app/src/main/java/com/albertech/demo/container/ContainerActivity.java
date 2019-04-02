package com.albertech.demo.container;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.albertech.demo.R;
import com.albertech.filewatch.api.IFileConstant;


public class ContainerActivity extends AppCompatActivity implements IFileConstant {

    private static final String TYPE = "type";


    public static void start(Context context, int type) {
        Intent intent = new Intent(context, ContainerActivity.class);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int type = getIntent().getIntExtra(TYPE, -1);
        switch (type) {
            case IMAGE:
                setContentView(R.layout.activity_container_image);
                break;
            case AUDIO:
                setContentView(R.layout.activity_container_audio);
                break;
            case VIDEO:
                setContentView(R.layout.activity_container_video);
                break;
            case DOC:
                setContentView(R.layout.activity_container_doc);
                break;
            case APK:
                setContentView(R.layout.activity_container_apk);
                break;
            case ZIP:
                setContentView(R.layout.activity_container_zip);
                break;
        }
    }

}
