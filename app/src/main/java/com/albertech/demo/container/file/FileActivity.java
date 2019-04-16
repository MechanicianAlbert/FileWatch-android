package com.albertech.demo.container.file;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.albertech.demo.R;
import com.albertech.demo.base.fragment.TitleFragment;
import com.albertech.filewatch.api.IFileConstant;


public class FileActivity extends AppCompatActivity implements IFileConstant {

    private static final String TYPE = "type";
    private TitleFragment mFragment;
    private Toolbar mTb;


    public static void start(Context context, int type) {
        Intent intent = new Intent(context, FileActivity.class);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        initCategoryContent();
    }

    @Override
    public void onBackPressed() {
        if (!mFragment.backToParent()) {
            super.onBackPressed();
        }
    }


    private void initCategoryContent() {
        int type = getIntent().getIntExtra(TYPE, -1);
        FragmentManager fm = getSupportFragmentManager();
        mFragment = FileFragmentFactory.getCategoryFragmentInstance(type);
        if (fm != null && mFragment != null) {
            fm.beginTransaction().replace(R.id.fl_category_fragment, mFragment).commitAllowingStateLoss();
        }
    }

}
