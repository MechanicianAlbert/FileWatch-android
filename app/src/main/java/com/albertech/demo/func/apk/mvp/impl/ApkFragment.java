package com.albertech.demo.func.apk.mvp.impl;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.fragment.TitleFragment;
import com.albertech.demo.func.apk.ApkBean;
import com.albertech.demo.func.apk.adapter.ApkAdapter;
import com.albertech.demo.func.apk.mvp.IApkContract;
import com.albertech.demo.util.Res;

import java.util.List;


public class ApkFragment extends TitleFragment implements IApkContract.IApkView {

    private final ApkAdapter ADAPTER = new ApkAdapter();


    private RecyclerView mRvHierarchy;

    private IApkContract.IApkPresenter mPresenter;


    @Override
    public String getTitle() {
        return Res.string(R.string.str_title_hierarchy);
    }

    @Override
    protected int layoutRese() {
        return R.layout.fragment_doc;
    }

    @Override
    protected void initView(View root) {
        mRvHierarchy = root.findViewById(R.id.rv_doc);
        mRvHierarchy.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void initData() {
        mRvHierarchy.setAdapter(ADAPTER);

        mPresenter = new ApkPresenter();
        mPresenter.init(getContext(), this);
        mPresenter.load();
    }

    @Override
    public void onResult(String path, List<ApkBean> list) {
        ADAPTER.updateData(list);
    }
}
