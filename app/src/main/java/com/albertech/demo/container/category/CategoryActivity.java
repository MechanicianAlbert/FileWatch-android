package com.albertech.demo.container.category;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.albertech.demo.R;
import com.albertech.demo.base.fragment.TitleFragment;
import com.albertech.filewatch.api.IFileConstant;


public class CategoryActivity extends AppCompatActivity implements IFileConstant {

    private static final String TYPE = "type";


    public static void start(Context context, int type) {
        Intent intent = new Intent(context, CategoryActivity.class);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        int type = getIntent().getIntExtra(TYPE, -1);

        FragmentManager mFm = getSupportFragmentManager();
        TitleFragment fragment = CategoryFragmentFactory.getCategoryFragmentInstance(type);

        if (mFm != null && fragment != null) {
            mFm.beginTransaction().replace(R.id.fl_category_fragment, fragment).commitAllowingStateLoss();
        }
    }

}
