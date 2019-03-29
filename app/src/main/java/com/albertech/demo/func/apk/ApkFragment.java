package com.albertech.demo.func.apk;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.albertech.demo.R;
import com.albertech.demo.base.fragment.TitleFragment;
import com.albertech.demo.util.Res;

public class ApkFragment extends TitleFragment {



    private EditText mEtSearch;
    private View mBtnSearch;
    private RecyclerView mRvHierarchy;

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
//        mRvHierarchy.setAdapter();
    }
}
