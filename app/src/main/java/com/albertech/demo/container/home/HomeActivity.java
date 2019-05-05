package com.albertech.demo.container.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.albertech.common.base.activity.BaseActivity;
import com.albertech.common.base.fragment.BaseFragment;
import com.albertech.demo.R;
import com.albertech.demo.func.base.impl.TabSelectingBar;
import com.albertech.demo.func.base.select.ISelectContract;
import com.albertech.demo.func.category.CategoryFragment;
import com.albertech.demo.func.hierarchy.mvp.impl.HierarchyFragment;


public class HomeActivity extends BaseActivity implements ISelectContract.ISelectView {

    private final String[] PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    private TabSelectingBar mTb;
    private ViewPager mVpMain;
    private TabLayout mTlMain;

    private BaseFragment mShowingFragment;


    public static void start(Context context) {
        context.startActivity(new Intent(context, HomeActivity.class));
    }


    @Override
    protected int layoutRes() {
        return R.layout.activity_home;
    }

    @Override
    protected void initPermission() {
        ActivityCompat.requestPermissions(this, PERMISSIONS, 0);
    }

    @Override
    protected void initView() {
        mTb = findViewById(R.id.tb_home);
        mVpMain = findViewById(R.id.vp_main);
        mTlMain = findViewById(R.id.tl_main);
    }

    @Override
    protected void initListener() {
        mTb.bindViewPager(mVpMain);
//        mTlMain.setupWithViewPager(mVpMain, true);
    }

    @Override
    protected void initData() {
        FragmentManager fm = getSupportFragmentManager();
        HomePagerAdapter adapter = new HomePagerAdapter(fm) {
            @Override
            public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                super.setPrimaryItem(container, position, object);
                if (mShowingFragment == null && object instanceof HierarchyFragment) {
                    mShowingFragment = (HierarchyFragment) object;
                    if (mTb != null) {
                        mTb.bindModel(((HierarchyFragment) mShowingFragment).getSelectionModel());
                    }
                }
            }
        };

        adapter.addFragment(CategoryFragment.newInstance());
        adapter.addFragment(HierarchyFragment.newInstance());
        mVpMain.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (!mShowingFragment.interceptBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void bindModel(ISelectContract.ISelectModel model) {
        // nothing to do
    }

    @Override
    public void onSelectingStatusChange(boolean isSelecting) {
        if (mTb != null) {
            mTb.onSelectingStatusChange(isSelecting);
        }
    }

    @Override
    public void onSelectionCountChange(int count, boolean hasSelectedAll) {
        if (mTb != null) {
            mTb.onSelectionCountChange(count, hasSelectedAll);
        }
    }

}
