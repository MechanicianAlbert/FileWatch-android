package com.albertech.demo.func.front;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.albertech.demo.base.fragment.TitleFragment;
import com.albertech.demo.func.category.CategoryFragment;

import java.util.ArrayList;
import java.util.List;

public class FrontPagerAdapter extends FragmentPagerAdapter {

    private final List<TitleFragment> FRAGMENTS = new ArrayList<>();


    public FrontPagerAdapter(FragmentManager fm) {
        super(fm);
        FRAGMENTS.add(CategoryFragment.newInstance());
        FRAGMENTS.add(CategoryFragment.newInstance());
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
