package com.albertech.demo.crud.query;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.albertech.demo.crud.query.hierarchy.HierarchyBean;
import com.albertech.demo.crud.query.hierarchy.HierarchyQueryMission;
import com.albertech.demo.crud.query.image.ImageBean;
import com.albertech.demo.crud.query.image.ImageQueryMission;
import com.albertech.demo.crud.query.video.VideoBean;
import com.albertech.demo.crud.query.video.VideoQueryMission;
import com.albertech.filewatch.api.FileHelper;
import com.albertech.filewatch.core.query.IFileQuery;

import java.util.List;


public class QueryHelper {

    private static QueryHelper INSTANCE = new QueryHelper();

    private QueryHelper() {
        if (INSTANCE != null) {
            throw new RuntimeException("This class cannot be instantiated more than once");
        }
    }

    public static QueryHelper getInstance() {
        return INSTANCE;
    }


    public static final String SD_CARD = Environment.getExternalStorageDirectory().getAbsolutePath();


    private IFileQuery mQueryer = FileHelper.createDefaultFileQuery();


    public void rImage(Context context, final String parentPath, final QueryCallback<ImageBean> callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndParentPath(context, new ImageQueryMission() {

                @Override
                public String parentPath() {
                    return !TextUtils.isEmpty(parentPath) ? parentPath : super.parentPath();
                }

                @Override
                public boolean recursive() {
                    return true;
                }

                @Override
                public void onQueryResult(String path, List list) {
                    super.onQueryResult(path, list);
                    callback.onResult(path, list);
                }
            });
        }
    }

    public void rVideo(Context context, final String parentPath, final QueryCallback<VideoBean> callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndParentPath(context, new VideoQueryMission() {

                @Override
                public String parentPath() {
                    return !TextUtils.isEmpty(parentPath) ? parentPath : super.parentPath();
                }

                @Override
                public boolean recursive() {
                    return true;
                }

                @Override
                public void onQueryResult(String path, List list) {
                    super.onQueryResult(path, list);
                    callback.onResult(path, list);
                }
            });
        }
    }

    public void dFile(Context context, final String parentPath, final QueryCallback<HierarchyBean> callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndParentPath(context, new HierarchyQueryMission() {

                @Override
                public String parentPath() {
                    return parentPath;
                }

                @Override
                public void onQueryResult(String path, List list) {
                    super.onQueryResult(path, list);
                    callback.onResult(path, list);
                }
            });
        }
    }

    public void dImage(Context context, final String parentPath, final QueryCallback<ImageBean> callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndParentPath(context, new ImageQueryMission() {

                @Override
                public String parentPath() {
                    return parentPath;
                }

                @Override
                public boolean recursive() {
                    return false;
                }

                @Override
                public void onQueryResult(String path, List list) {
                    super.onQueryResult(path, list);
                    callback.onResult(path, list);
                }
            });
        }
    }


}
