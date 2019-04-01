package com.albertech.demo.func.hierarchy;

import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.albertech.demo.R;
import com.albertech.demo.base.fragment.TitleFragment;
import com.albertech.demo.crud.query.QueryCallback;
import com.albertech.demo.crud.query.QueryHelper;
import com.albertech.demo.crud.query.hierarchy.HierarchyBean;
import com.albertech.demo.func.category.CategoryFragment;
import com.albertech.demo.func.hierarchy.adapter.HierarchyAdapter;
import com.albertech.demo.util.Res;

import java.util.List;
import java.util.Stack;


public class HierarchyFragment extends TitleFragment {

    private final HierarchyAdapter ADAPTER = new HierarchyAdapter() {
        @Override
        public boolean onItemClick(int position, HierarchyBean hierarchyBean) {
            if (hierarchyBean.isDirectory()) {
                mHierarchyStack.push(mCurrentPath);
                mCurrentPath = hierarchyBean.path;
                changePath(mCurrentPath);
            }
            return false;
        }
    };
    private final String SD_CARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    private final Stack<String> mHierarchyStack = new Stack<>();


    private EditText mEtSearch;
    private View mBtnSearch;
    private RecyclerView mRvHierarchy;

    private String mCurrentPath;


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
        mHierarchyStack.push(SD_CARD_PATH);
        changePath(SD_CARD_PATH);
    }

    public boolean backToParent() {
        if (!mHierarchyStack.isEmpty()) {
            mCurrentPath = mHierarchyStack.pop();
            changePath(mCurrentPath);
            if (mHierarchyStack.isEmpty()) {
                mHierarchyStack.push(SD_CARD_PATH);
            }
            return true;
        } else {
            return false;
        }
    }


    private void changePath(String path) {
        QueryHelper.getInstance().dFile(getContext(), path, new QueryCallback<HierarchyBean>() {
            @Override
            public void onResult(String path, final List<HierarchyBean> list) {
                mRvHierarchy.post(new Runnable() {
                    @Override
                    public void run() {
                        ADAPTER.updateData(list);
                    }
                });
            }
        });
    }
}
