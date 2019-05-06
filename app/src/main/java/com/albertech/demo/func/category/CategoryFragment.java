package com.albertech.demo.func.category;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.albertech.common.base.fragment.BaseFragment;
import com.albertech.demo.R;
import com.albertech.demo.container.file.FileActivity;
import com.albertech.demo.func.category.adapter.CategoryAdapter;
import com.albertech.demo.util.Res;
import com.albertech.filehelper.api.IFileConstant;


public class CategoryFragment extends BaseFragment {

    private final CategoryAdapter ADAPTER = new CategoryAdapter() {
        @Override
        public boolean onItemClick(int position, CategoryBean categoryBean) {
            String name = categoryBean.NAME;
            if (Res.string(R.string.str_category_image).equals(name)) {
                FileActivity.start(getContext(), IFileConstant.IMAGE);
            } else if (Res.string(R.string.str_category_audio).equals(name)) {
                FileActivity.start(getContext(), IFileConstant.AUDIO);
            } else if (Res.string(R.string.str_category_video).equals(name)) {
                FileActivity.start(getContext(), IFileConstant.VIDEO);
            } else if (Res.string(R.string.str_category_doc).equals(categoryBean.NAME)) {
                FileActivity.start(getContext(), IFileConstant.DOC);
            } else if (Res.string(R.string.str_category_apk).equals(categoryBean.NAME)) {
                FileActivity.start(getContext(), IFileConstant.APK);
            } else if (Res.string(R.string.str_category_zip).equals(categoryBean.NAME)) {
                FileActivity.start(getContext(), IFileConstant.ZIP);
            }
            return false;
        }
    };


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
    protected int layoutRes() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initView(View root) {
        mBtnSearch = root.findViewById(R.id.btn_search);
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
