package com.albertech.demo.container.home;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.albertech.demo.base.fragment.TitleFragment;
import com.albertech.demo.func.category.CategoryFragment;
import com.albertech.demo.func.hierarchy.HierarchyFragment;

import java.util.ArrayList;
import java.util.List;

public class HomePagerAdapter extends FragmentPagerAdapter {

    private final List<TitleFragment> FRAGMENTS = new ArrayList<>();


    HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    void addFragment(TitleFragment fragment) {
        FRAGMENTS.add(fragment);
//        notifyDataSetChanged();
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
