package com.albertech.demo.func.base.impl;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.fragment.TitleFragment;
import com.albertech.demo.base.recycler.selectable.SelectableHolder;
import com.albertech.demo.func.base.IFileContract;
import com.albertech.demo.func.base.select.ISelectContract;
import com.albertech.demo.util.Res;

import java.util.List;


public abstract class BaseFileViewFragment<Adapter extends BaseSelectionAdapter<? extends SelectableHolder, Bean>, Bean>
        extends TitleFragment
        implements IFileContract.IFileView<Bean> {

    private final Adapter ADAPTER = createAdapter();


    private FileSelectingBar mTb;
    private RecyclerView mRv;

    private IFileContract.IFilePresenter<Bean> mPresenter;

    private boolean mIsSelecting;


    @Override
    protected int layoutRese() {
        return R.layout.fragment_file;
    }

    @Override
    protected void initView(View root) {
        mTb = root.findViewById(R.id.tb_file);

        mRv = root.findViewById(R.id.rv_doc);
        mRv.setLayoutManager(getLayoutManager());
    }

    @Override
    protected void initData() {
        mTb.setTitle(getTitle());
        mTb.bindModel(ADAPTER);

        mRv.setAdapter(ADAPTER);

        mPresenter = createPresenter();
        mPresenter.init(getContext(), this);
        mPresenter.load();
    }

    @Override
    public void onResult(String path, List<Bean> list) {
        ADAPTER.updateData(list);
    }


    @Override
    public void onSelectingStatusChange(boolean isSelecting) {
        mIsSelecting = isSelecting;
        mTb.onSelectingStatusChange(isSelecting);
    }

    @Override
    public void onSelectionCountChange(int count, boolean hasSelectedAll) {
        mTb.onSelectionCountChange(count, hasSelectedAll);
    }

    @Override
    public void bindModel(ISelectContract.ISelectModel model) {
        // nothing to do
    }


    public boolean backToParent() {
        final boolean isSelecting = mIsSelecting;
        if (isSelecting) {
            ADAPTER.stopSelecting();
        }
        return isSelecting;
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    }


    protected abstract IFileContract.IFilePresenter<Bean> createPresenter();

    protected abstract Adapter createAdapter();

}
