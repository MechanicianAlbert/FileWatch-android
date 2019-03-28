package com.albertech.demo.func.front;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.albertech.demo.R;
import com.albertech.demo.base.activity.BaseActivity;

public class FrontActivity extends BaseActivity {

    private ViewPager mVpMain;
    private TabLayout mTlMain;


    public static void start(Context context) {
        context.startActivity(new Intent(context, FrontActivity.class));
    }


    @Override
    protected int layoutRese() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mVpMain = findViewById(R.id.vp_main);
        mTlMain = findViewById(R.id.tl_main);
    }

    @Override
    protected void initListener() {
        mTlMain.setupWithViewPager(mVpMain, true);
    }

    @Override
    protected void initData() {
        FragmentManager fm = getSupportFragmentManager();
        FrontPagerAdapter adapter = new FrontPagerAdapter(fm);
        mVpMain.setAdapter(adapter);
    }

}
