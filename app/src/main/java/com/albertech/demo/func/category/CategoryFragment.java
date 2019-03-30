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
import com.albertech.demo.crud.query.QueryHelper;
import com.albertech.demo.crud.query.QueryCallback;
import com.albertech.demo.crud.query.image.ImageBean;

import java.io.File;
import java.util.List;


public class CategoryFragment extends TitleFragment {

    private final CategoryAdapter ADAPTER = new CategoryAdapter() {
        @Override
        public boolean onItemClick(int position, CategoryBean categoryBean) {
            if (Res.string(R.string.str_category_image).equals(categoryBean.NAME)) {
                ContainerActivity.start(getContext());
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
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = QueryHelper.SD_CARD + File.separator + "AAA";
                path = "";
                QueryHelper.getInstance().rImage(getContext(), path, new QueryCallback<ImageBean>() {
                    @Override
                    public void onResult(String path, List<ImageBean> list) {

                    }
                });
            }
        });
    }

    @Override
    protected void initData() {
        mRvCategory.setAdapter(ADAPTER);
    }

}
