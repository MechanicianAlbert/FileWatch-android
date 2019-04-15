package com.albertech.demo.container.category;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.albertech.demo.R;
import com.albertech.demo.func.base.impl.BaseFileViewFragment;
import com.albertech.filewatch.api.IFileConstant;


public class CategoryActivity extends AppCompatActivity implements IFileConstant {

    private static final String TYPE = "type";
    private BaseFileViewFragment mFragment;
    private Toolbar mTb;


    public static void start(Context context, int type) {
        Intent intent = new Intent(context, CategoryActivity.class);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        initToolBar();
        initCategoryContent();
    }

    @Override
    public void onBackPressed() {
        if (!mFragment.backToParent()) {
            super.onBackPressed();
        }
    }


    private void initToolBar() {
        mTb = findViewById(R.id.tb);
        setSupportActionBar(mTb);
        mTb.inflateMenu(R.menu.menu_normal);
        mTb.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        mTb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "AAA", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initCategoryContent() {
        int type = getIntent().getIntExtra(TYPE, -1);
        FragmentManager fm = getSupportFragmentManager();
        mFragment = CategoryFragmentFactory.getCategoryFragmentInstance(type);
        if (fm != null && mFragment != null) {
            fm.beginTransaction().replace(R.id.fl_category_fragment, mFragment).commitAllowingStateLoss();
        }
    }

}
