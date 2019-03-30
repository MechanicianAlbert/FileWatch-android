package com.albertech.demo.func.category;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.albertech.demo.R;
import com.albertech.demo.base.fragment.TitleFragment;
import com.albertech.demo.bean.BaseFileBean;
import com.albertech.demo.util.Res;
import com.albertech.demo.util.ForEachUtil;
import com.albertech.demo.util.query.FileQueryHelper;
import com.albertech.demo.util.query.QueryCallback;

import java.io.File;
import java.util.List;


public class CategoryFragment extends TitleFragment {

    private final CategoryAdapter ADAPTER = new CategoryAdapter();


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
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = FileQueryHelper.SD_CARD + File.separator + "AAA";
                FileQueryHelper.getInstance().dDoc(getContext(), path, new QueryCallback<BaseFileBean>() {
                    @Override
                    public void onResult(String path, List<BaseFileBean> list) {
                        Log.e("AAA", "Parent: " + path);
                        ForEachUtil.forEach(list, new ForEachUtil.ItemHandler<BaseFileBean>() {

                            @Override
                            public void handle(BaseFileBean item) {
                                Log.e("AAA", "Path: " + item.path);
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    protected void initData() {
        mRvCategory.setAdapter(ADAPTER);
    }

    private void print(List<String> list) {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Log.e("AAA", "Path: " + list.get(i));
            }
        }
    }
}
