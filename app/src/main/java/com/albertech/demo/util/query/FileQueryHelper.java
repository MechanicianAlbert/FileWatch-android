package com.albertech.demo.util.query;

import android.os.Environment;

import com.albertech.demo.util.Res;
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
        mQueryer = FileHelper.createDefaultFileQuery(Res.context());
    }


    public void dImage(String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(mQueryer.IMAGE, path, new IFileQureyListener() {
                @Override
                public void onQueryResult(String path, List<String> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

    public void dAudio(String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(mQueryer.AUDIO, path, new IFileQureyListener() {
                @Override
                public void onQueryResult(String path, List<String> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

    public void dVideo(String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(mQueryer.VIDEO, path, new IFileQureyListener() {
                @Override
                public void onQueryResult(String path, List<String> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

    public void dDoc(String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(mQueryer.DOC, path, new IFileQureyListener() {
                @Override
                public void onQueryResult(String path, List<String> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

    public void dZip(String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(mQueryer.ZIP, path, new IFileQureyListener() {
                @Override
                public void onQueryResult(String path, List<String> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

    public void dApk(String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(mQueryer.APK, path, new IFileQureyListener() {
                @Override
                public void onQueryResult(String path, List<String> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

    public void dFile(String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(mQueryer.FILE, path, new IFileQureyListener() {
                @Override
                public void onQueryResult(String path, List<String> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

    public void rImage(String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(mQueryer.IMAGE, path, true, new IFileQureyListener() {
                @Override
                public void onQueryResult(String path, List<String> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

    public void rAudio(String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(mQueryer.AUDIO, path, true, new IFileQureyListener() {
                @Override
                public void onQueryResult(String path, List<String> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

    public void rVideo(String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(mQueryer.VIDEO, path, true, new IFileQureyListener() {
                @Override
                public void onQueryResult(String path, List<String> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

    public void rDoc(String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(mQueryer.DOC, path, true, new IFileQureyListener() {
                @Override
                public void onQueryResult(String path, List<String> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

    public void rZip(String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(mQueryer.ZIP, path, true, new IFileQureyListener() {
                @Override
                public void onQueryResult(String path, List<String> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

    public void rApk(String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(mQueryer.APK, path, true, new IFileQureyListener() {
                @Override
                public void onQueryResult(String path, List<String> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

    public void rFile(String path, final QueryCallback callback) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndPath(mQueryer.FILE, path, true, new IFileQureyListener() {
                @Override
                public void onQueryResult(String path, List<String> list) {
                    if (callback != null) {
                        callback.onResult(path, list);
                    }
                }
            });
        }
    }

}
