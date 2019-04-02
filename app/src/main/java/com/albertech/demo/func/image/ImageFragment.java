package com.albertech.demo.func.image;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.albertech.demo.R;
import com.albertech.demo.base.fragment.TitleFragment;
import com.albertech.demo.crud.query.QueryCallback;
import com.albertech.demo.crud.query.QueryHelper;
import com.albertech.demo.crud.query.image.ImageBean;
import com.albertech.demo.func.image.adapter.ImageAdapter;
import com.albertech.demo.util.Res;
import com.albertech.filewatch.api.FileHelper;
import com.albertech.filewatch.api.IFileConstant;
import com.albertech.filewatch.api.IFileWatchSubscriber;
import com.albertech.filewatch.api.IFileWatchUnsubscribe;
import com.albertech.filewatch.core.query.IFileType;

import java.util.List;


public class ImageFragment extends TitleFragment {

    private final ImageAdapter ADAPTER = new ImageAdapter() {
        @Override
        public boolean onItemClick(int position, ImageBean imageBean) {
            return false;
        }
    };


    private RecyclerView mRvImage;
    private IFileWatchUnsubscribe mUnsunscribe;


    @Override
    public String getTitle() {
        return Res.string(R.string.str_category_image);
    }

    @Override
    protected int layoutRese() {
        return R.layout.fragment_image;
    }

    @Override
    protected void initView(View root) {
        mRvImage = root.findViewById(R.id.rv_image);
        mRvImage.setLayoutManager(new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void initData() {
        mRvImage.setAdapter(ADAPTER);
        update();
    }

    @Override
    protected void release() {
        unsubscribeFileWatch();
    }

    private void subscribeFileWatch() {
        mUnsunscribe = FileHelper.subscribeFileWatch(getContext(), new IFileWatchSubscriber() {
            @Override
            public void onEvent(int event, String parentPath, String childPath) {

            }

            @Override
            public void onScanResult(String parentPath) {
                update();
            }

            @Override
            public void onQueryResult(String parentPath, List<String> list) {

            }
        }, IFileConstant.IMAGE, null);
    }

    private void unsubscribeFileWatch() {
        if (mUnsunscribe != null) {
            mUnsunscribe.unsubscribe();
        }
    }

    private void update() {
        QueryHelper.getInstance().rImage(getContext(), null, new QueryCallback<ImageBean>() {
            @Override
            public void onResult(String path, final List<ImageBean> list) {
                mRvImage.post(new Runnable() {
                    @Override
                    public void run() {
                        ADAPTER.updateData(list);
                    }
                });
            }
        });
    }
}
