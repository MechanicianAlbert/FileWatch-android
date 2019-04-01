package com.albertech.demo.container.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.albertech.demo.R;
import com.albertech.demo.base.activity.BaseActivity;
import com.albertech.demo.func.category.CategoryFragment;
import com.albertech.demo.func.hierarchy.HierarchyFragment;

public class HomeActivity extends BaseActivity {

    private final String[] PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    private ViewPager mVpMain;
    private TabLayout mTlMain;

    private boolean mBackPressedAsNormal;
    private HierarchyFragment mHf;


    public static void start(Context context) {
        context.startActivity(new Intent(context, HomeActivity.class));
    }


    @Override
    protected int layoutRese() {
        return R.layout.activity_home;
    }

    @Override
    protected void initPermission() {
        ActivityCompat.requestPermissions(this, PERMISSIONS, 0);
    }

    @Override
    protected void initView() {
        mVpMain = findViewById(R.id.vp_main);
        mTlMain = findViewById(R.id.tl_main);
    }

    @Override
    protected void initListener() {
        mTlMain.setupWithViewPager(mVpMain, true);
        mVpMain.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

            }
        });
    }

    @Override
    protected void initData() {
        FragmentManager fm = getSupportFragmentManager();
        HomePagerAdapter adapter = new HomePagerAdapter(fm);
        adapter.addFragment(CategoryFragment.newInstance());
        mHf = HierarchyFragment.newInstance();
        adapter.addFragment(mHf);
        mVpMain.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (mBackPressedAsNormal) {
            super.onBackPressed();
        } else {
            if (!mHf.backToParent()) {
                super.onBackPressed();
            }
        }
    }
}
