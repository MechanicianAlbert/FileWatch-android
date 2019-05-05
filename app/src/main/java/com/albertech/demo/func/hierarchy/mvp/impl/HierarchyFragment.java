package com.albertech.demo.func.hierarchy.mvp.impl;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.albertech.common.base.fragment.BaseFragment;
import com.albertech.demo.R;
import com.albertech.demo.container.home.HomeActivity;
import com.albertech.demo.func.base.select.ISelectContract;
import com.albertech.demo.func.hierarchy.HierarchyBean;
import com.albertech.demo.func.hierarchy.adapter.HierarchyAdapter;
import com.albertech.demo.func.hierarchy.mvp.IHierarchyContract;
import com.albertech.demo.util.Res;

import java.util.List;


public class HierarchyFragment extends BaseFragment implements IHierarchyContract.IHierarchyView, ISelectContract.ISelectView {

    private final HierarchyAdapter ADAPTER = new HierarchyAdapter(this) {
        @Override
        public void onItemClickNotSelecting(int position, HierarchyBean bean) {
            if (bean.isDirectory()) {
                mPresenter.loadPath(bean.path);
            }
        }
    };


    private View mBtnSearch;
    private RecyclerView mRvHierarchy;

    private IHierarchyContract.IHierarchyPresenter mPresenter;


    public static HierarchyFragment newInstance() {
        HierarchyFragment instance = new HierarchyFragment();
        return instance;
    }


    @Override
    public String getTitle() {
        return Res.string(R.string.str_title_hierarchy);
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_hierarchy;
    }

    @Override
    protected void initView(View root) {
        mBtnSearch = root.findViewById(R.id.btn_search);
        mRvHierarchy = root.findViewById(R.id.rv_hierarchy);
        mRvHierarchy.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void initData() {
        mRvHierarchy.setAdapter(ADAPTER);

        mPresenter = new HierarchyPresenter();
        mPresenter.init(getContext(), this);
        mPresenter.loadPath(IHierarchyContract.SD_CARD_PATH);
    }

    @Override
    protected void release() {
        super.release();
        if (mPresenter != null) {
            mPresenter.release();
            mPresenter = null;
        }
    }

    @Override
    public boolean interceptBackPressed() {
        final  boolean isSelecting = ADAPTER.isSelecting();
        if (isSelecting) {
            ADAPTER.stopSelecting();
            return true;
        } else {
            return mPresenter.backToParent();
        }
    }


    @Override
    public void onResult(String path, List<HierarchyBean> list) {
        ADAPTER.updateData(list);
    }

    @Override
    public void bindModel(ISelectContract.ISelectModel model) {

    }

    @Override
    public void onSelectingStatusChange(boolean isSelecting) {
        Activity activity = getActivity();
        if (activity instanceof HomeActivity) {
            ((HomeActivity) activity).onSelectingStatusChange(isSelecting);
        }
    }

    @Override
    public void onSelectionCountChange(int count, boolean hasSelectedAll) {
        Activity activity = getActivity();
        if (activity instanceof HomeActivity) {
            ((HomeActivity) activity).onSelectionCountChange(count, hasSelectedAll);
        }
    }


    public ISelectContract.ISelectModel getSelectionModel() {
        return ADAPTER;
    }

}
