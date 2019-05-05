package com.albertech.demo.container.home;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.albertech.common.base.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;


public class HomePagerAdapter extends FragmentPagerAdapter {

    private final List<BaseFragment> FRAGMENTS = new ArrayList<>();


    HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    void addFragment(BaseFragment fragment) {
        FRAGMENTS.add(fragment);
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return FRAGMENTS.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return FRAGMENTS.size();
    }

    @Override
    public Fragment getItem(int position) {
        return FRAGMENTS.get(position);
    }
}
