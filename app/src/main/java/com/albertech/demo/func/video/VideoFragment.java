package com.albertech.demo.func.video;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.fragment.TitleFragment;
import com.albertech.demo.crud.query.QueryCallback;
import com.albertech.demo.crud.query.QueryHelper;
import com.albertech.demo.crud.query.video.VideoBean;
import com.albertech.demo.func.video.adapter.VideoAdapter;
import com.albertech.demo.util.Res;
import com.albertech.filewatch.api.FileHelper;
import com.albertech.filewatch.api.IFileConstant;
import com.albertech.filewatch.api.IFileWatchSubscriber;
import com.albertech.filewatch.api.IFileWatchUnsubscribe;
import com.albertech.filewatch.core.query.IFileType;

import java.util.List;


public class VideoFragment extends TitleFragment {

    private final VideoAdapter ADAPTER = new VideoAdapter() {
        @Override
        public boolean onItemClick(int position, VideoBean videoBean) {
            return false;
        }
    };


    private RecyclerView mRvVideo;
    private IFileWatchUnsubscribe mUnsunscribe;


    @Override
    public String getTitle() {
        return Res.string(R.string.str_category_video);
    }

    @Override
    protected int layoutRese() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initView(View root) {
        mRvVideo = root.findViewById(R.id.rv_video);
        mRvVideo.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void initData() {
        mRvVideo.setAdapter(ADAPTER);
//        subscribeFileWatch();
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
        QueryHelper.getInstance().rVideo(getContext(), null, new QueryCallback<VideoBean>() {
            @Override
            public void onResult(String path, final List<VideoBean> list) {
                mRvVideo.post(new Runnable() {
                    @Override
                    public void run() {
                        ADAPTER.updateData(list);
                    }
                });
            }
        });
    }
}
