package com.albertech.demo.util.query;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;

import com.albertech.demo.bean.BaseFileBean;
import com.albertech.filewatch.api.FileHelper;
import com.albertech.filewatch.core.query.IFileQuery;
import com.albertech.filewatch.core.query.IFileQureyListener;

import java.util.List;


public class FileQueryHelper {

    private static FileQueryHelper INSTANCE = new FileQueryHelper();

    private FileQueryHelper() {
        if (INSTANCE != null) {
            throw new RuntimeException("This class cannot be instantiated more than once");
        }
        init();
    }

    public static FileQueryHelper getInstance() {
        return INSTANCE;
    }


    public static final String SD_CARD = Environment.getExternalStorageDirectory().getAbsolutePath();


    private IFileQuery mQueryer;


    private void init() {
        mQueryer = FileHelper.createDefaultFileQuery();
    }


    public void dImage(Context context, String path, final QueryCallback<BaseFileBean> callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(context, mQueryer.IMAGE, path, new IFileQureyListener<BaseFileBean>() {

                @Override
                public BaseFileBean transfer(Cursor cursor) {
                    BaseFileBean bean = new BaseFileBean();
                    bean.path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                    return bean;
                }

                @Override
                public void onQueryResult(String path, List<BaseFileBean> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

    public void dAudio(Context context, String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(context, mQueryer.AUDIO, path, new IFileQureyListener<BaseFileBean>() {

                @Override
                public BaseFileBean transfer(Cursor cursor) {
                    BaseFileBean bean = new BaseFileBean();
                    bean.path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                    return bean;
                }

                @Override
                public void onQueryResult(String path, List<BaseFileBean> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

    public void dVideo(Context context, String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(context, mQueryer.VIDEO, path, new IFileQureyListener<BaseFileBean>() {

                @Override
                public BaseFileBean transfer(Cursor cursor) {
                    BaseFileBean bean = new BaseFileBean();
                    bean.path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                    return bean;
                }

                @Override
                public void onQueryResult(String path, List<BaseFileBean> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

    public void dDoc(Context context, String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(context, mQueryer.DOC, path, new IFileQureyListener<BaseFileBean>() {

                @Override
                public BaseFileBean transfer(Cursor cursor) {
                    BaseFileBean bean = new BaseFileBean();
                    bean.path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                    return bean;
                }

                @Override
                public void onQueryResult(String path, List<BaseFileBean> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

    public void dZip(Context context, String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(context, mQueryer.ZIP, path, new IFileQureyListener<BaseFileBean>() {

                @Override
                public BaseFileBean transfer(Cursor cursor) {
                    BaseFileBean bean = new BaseFileBean();
                    bean.path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                    return bean;
                }

                @Override
                public void onQueryResult(String path, List<BaseFileBean> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

    public void dApk(Context context, String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(context, mQueryer.APK, path, new IFileQureyListener<BaseFileBean>() {

                @Override
                public BaseFileBean transfer(Cursor cursor) {
                    BaseFileBean bean = new BaseFileBean();
                    bean.path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                    return bean;
                }

                @Override
                public void onQueryResult(String path, List<BaseFileBean> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

    public void dFile(Context context, String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(context, mQueryer.FILE, path, new IFileQureyListener<BaseFileBean>() {

                @Override
                public BaseFileBean transfer(Cursor cursor) {
                    BaseFileBean bean = new BaseFileBean();
                    bean.path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                    return bean;
                }

                @Override
                public void onQueryResult(String path, List<BaseFileBean> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

    public void rImage(Context context, String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(context, mQueryer.IMAGE, path, true, new IFileQureyListener<BaseFileBean>() {

                @Override
                public BaseFileBean transfer(Cursor cursor) {
                    BaseFileBean bean = new BaseFileBean();
                    bean.path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                    return bean;
                }

                @Override
                public void onQueryResult(String path, List<BaseFileBean> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

    public void rAudio(Context context, String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(context, mQueryer.AUDIO, path, true, new IFileQureyListener<BaseFileBean>() {

                @Override
                public BaseFileBean transfer(Cursor cursor) {
                    BaseFileBean bean = new BaseFileBean();
                    bean.path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                    return bean;
                }

                @Override
                public void onQueryResult(String path, List<BaseFileBean> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

    public void rVideo(Context context, String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(context, mQueryer.VIDEO, path, true, new IFileQureyListener<BaseFileBean>() {

                @Override
                public BaseFileBean transfer(Cursor cursor) {
                    BaseFileBean bean = new BaseFileBean();
                    bean.path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                    return bean;
                }

                @Override
                public void onQueryResult(String path, List<BaseFileBean> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

    public void rDoc(Context context, String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(context, mQueryer.DOC, path, true, new IFileQureyListener<BaseFileBean>() {

                @Override
                public BaseFileBean transfer(Cursor cursor) {
                    BaseFileBean bean = new BaseFileBean();
                    bean.path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                    return bean;
                }

                @Override
                public void onQueryResult(String path, List<BaseFileBean> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

    public void rZip(Context context, String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(context, mQueryer.ZIP, path, true, new IFileQureyListener<BaseFileBean>() {

                @Override
                public BaseFileBean transfer(Cursor cursor) {
                    BaseFileBean bean = new BaseFileBean();
                    bean.path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                    return bean;
                }

                @Override
                public void onQueryResult(String path, List<BaseFileBean> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

    public void rApk(Context context, String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(context, mQueryer.APK, path, true, new IFileQureyListener<BaseFileBean>() {

                @Override
                public BaseFileBean transfer(Cursor cursor) {
                    BaseFileBean bean = new BaseFileBean();
                    bean.path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                    return bean;
                }

                @Override
                public void onQueryResult(String path, List<BaseFileBean> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

    public void rFile(Context context, String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(context, mQueryer.FILE, path, true, new IFileQureyListener<BaseFileBean>() {

                @Override
                public BaseFileBean transfer(Cursor cursor) {
                    BaseFileBean bean = new BaseFileBean();
                    bean.path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                    return bean;
                }

                @Override
                public void onQueryResult(String path, List<BaseFileBean> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

}
