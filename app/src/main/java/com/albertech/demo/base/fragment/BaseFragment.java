package com.albertech.demo.base.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(layoutRese(), container, false);
        initView(root);
        initListener();
        initData();
        return root;
    }


    protected void initView(View root) {

    }

    protected void initListener() {

    }

    protected void initData() {

    }


    protected abstract int layoutRese();
}
