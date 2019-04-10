package com.albertech.demo.func.doc.mvp.impl;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.fragment.TitleFragment;
import com.albertech.demo.func.doc.DocBean;
import com.albertech.demo.func.doc.adapter.DocAdapter;
import com.albertech.demo.func.doc.mvp.IDocContract;
import com.albertech.demo.util.Res;

import java.util.List;



public class DocFragment extends TitleFragment implements IDocContract.IDocView {

    private final DocAdapter ADAPTER = new DocAdapter();


    private RecyclerView mRvHierarchy;

    private IDocContract.IDocPresenter mPresenter;


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

        mPresenter = new DocPresenter();
        mPresenter.init(getContext(), this);
        mPresenter.load();
    }

    @Override
    public void onResult(String path, List<DocBean> list) {
        ADAPTER.updateData(list);
    }
}
