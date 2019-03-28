package com.albertech.demo.func.category;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.albertech.demo.R;
import com.albertech.demo.base.fragment.TitleFragment;



public class CategoryFragment extends TitleFragment {

    private EditText mEtSearch;
    private View mBtnSearch;
    private RecyclerView mRvCategory;


    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }


    @Override
    public String getTitle() {
        return "分类";
    }

    @Override
    protected int layoutRese() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initView(View root) {
        mEtSearch = root.findViewById(R.id.et_category_search);
        mBtnSearch = root.findViewById(R.id.btn_category_search);
        mRvCategory = root.findViewById(R.id.rv_category);
        mRvCategory.setLayoutManager(new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void initData() {
        CategoryAdapter adapter = new CategoryAdapter(getContext());
        mRvCategory.setAdapter(adapter);
    }
}
