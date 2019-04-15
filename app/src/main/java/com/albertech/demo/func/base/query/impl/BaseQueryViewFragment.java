package com.albertech.demo.func.base.query.impl;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.fragment.TitleFragment;
import com.albertech.demo.base.recycler.SelectableHolder;
import com.albertech.demo.base.recycler.SelectableRecyclerAdapter;
import com.albertech.demo.func.base.query.IBaseQueryContract;
import com.albertech.demo.util.Res;

import java.util.List;


public abstract class BaseQueryViewFragment<Adapter extends SelectableRecyclerAdapter<? extends SelectableHolder, Bean>, Bean> extends TitleFragment implements IBaseQueryContract.IBaseQueryView<Bean> {

    private final Adapter ADAPTER = createAdapter();


    private RecyclerView mRv;

    private IBaseQueryContract.IBaseQueryPresenter<Bean> mPresenter;


    @Override
    public String getTitle() {
        return Res.string(R.string.str_title_hierarchy);
    }

    @Override
    protected int layoutRese() {
        return R.layout.fragment_file;
    }

    @Override
    protected void initView(View root) {
        mRv = root.findViewById(R.id.rv_doc);
        mRv.setLayoutManager(getLayoutManager());
    }

    @Override
    protected void initData() {
        mRv.setAdapter(ADAPTER);

        mPresenter = createPresenter();
        mPresenter.init(getContext(), this);
        mPresenter.load();
    }

    @Override
    public void onResult(String path, List<Bean> list) {
        ADAPTER.updateData(list);
    }


    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    }


    protected abstract IBaseQueryContract.IBaseQueryPresenter<Bean> createPresenter();

    protected abstract Adapter createAdapter();

}
