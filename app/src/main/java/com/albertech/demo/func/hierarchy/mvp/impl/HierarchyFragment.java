package com.albertech.demo.func.hierarchy.mvp.impl;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.albertech.demo.R;
import com.albertech.demo.base.fragment.TitleFragment;
import com.albertech.demo.func.hierarchy.HierarchyBean;
import com.albertech.demo.func.hierarchy.adapter.HierarchyAdapter;
import com.albertech.demo.func.hierarchy.mvp.IHierarchyContract;
import com.albertech.demo.util.Res;

import java.util.List;



public class HierarchyFragment extends TitleFragment implements IHierarchyContract.IHierarchyView {

    private final HierarchyAdapter ADAPTER = new HierarchyAdapter() {
        @Override
        public boolean onItemClick(int position, HierarchyBean bean) {
            if (bean.isDirectory()) {
                mPresenter.loadPath(bean.path);
            }
            return false;
        }
    };


    private EditText mEtSearch;
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
    protected int layoutRese() {
        return R.layout.fragment_hierarchy;
    }

    @Override
    protected void initView(View root) {
        mEtSearch = root.findViewById(R.id.et_hierarchy_search);
        mBtnSearch = root.findViewById(R.id.btn_hierarchy_search);
        mRvHierarchy = root.findViewById(R.id.rv_hierarchy);
        mRvHierarchy.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void initData() {
        mRvHierarchy.setAdapter(ADAPTER);

        mPresenter = new HierarchyPresenter();
        mPresenter.init(getContext(), this);
        mPresenter.loadPath(IHierarchyContract.DEFAULT_PARENT_PATH);
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
    public void onResult(String path, List<HierarchyBean> list) {
        ADAPTER.updateData(list);
    }


    public boolean backToParent() {
        return mPresenter.backToParent();
    }

}
