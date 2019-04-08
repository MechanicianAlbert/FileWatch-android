package com.albertech.demo.func.category;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.albertech.demo.R;
import com.albertech.demo.base.fragment.TitleFragment;
import com.albertech.demo.container.ContainerActivity;
import com.albertech.demo.util.Res;
import com.albertech.filewatch.api.IFileConstant;



public class CategoryFragment extends TitleFragment {

    private final CategoryAdapter ADAPTER = new CategoryAdapter() {
        @Override
        public boolean onItemClick(int position, CategoryBean categoryBean) {
            if (Res.string(R.string.str_category_image).equals(categoryBean.NAME)) {
                ContainerActivity.start(getContext(), IFileConstant.IMAGE);
            } else if (Res.string(R.string.str_category_audio).equals(categoryBean.NAME)) {
                ContainerActivity.start(getContext(), IFileConstant.AUDIO);
            } else if (Res.string(R.string.str_category_video).equals(categoryBean.NAME)) {
                ContainerActivity.start(getContext(), IFileConstant.VIDEO);
            }
            return false;
        }
    };


    private EditText mEtSearch;
    private View mBtnSearch;
    private RecyclerView mRvCategory;


    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }


    @Override
    public String getTitle() {
        return Res.string(R.string.str_title_category);
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
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mRvCategory.setAdapter(ADAPTER);
    }

}
