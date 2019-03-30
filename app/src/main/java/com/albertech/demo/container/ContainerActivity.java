package com.albertech.demo.container;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.albertech.demo.R;



public class ContainerActivity extends AppCompatActivity {


    public static void start(Context context) {
        context.startActivity(new Intent(context, ContainerActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
    }
}
