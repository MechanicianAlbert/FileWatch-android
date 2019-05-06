package com.albertech.filehelper.core.query;

import android.content.Context;
import android.util.SparseArray;

import com.albertech.filehelper.api.IFileQueryMisson;
import com.albertech.filehelper.core.CommonExecutor;
import com.albertech.filehelper.core.query.cursor.ICursorFactory;
import com.albertech.filehelper.core.query.cursor.impl.ApkCursorFactory;
import com.albertech.filehelper.core.query.cursor.impl.AudioCursorFactory;
import com.albertech.filehelper.core.query.cursor.impl.DocCursorFactory;
import com.albertech.filehelper.core.query.cursor.impl.FileCursorFactory;
import com.albertech.filehelper.core.query.cursor.impl.ImageCursorFactory;
import com.albertech.filehelper.core.query.cursor.impl.VideoCursorFactory;
import com.albertech.filehelper.core.query.cursor.impl.ZipCursorFactory;

/**
 * 文件查询器类
 */
public class FileQueryer implements IFileQuery {

    /**
     * 游标工厂集合
     * 键为查询文件类型
     * 值为游标工厂
     */
    private static final SparseArray<ICursorFactory> CURSOR_FACTORIES = new SparseArray<>();


    public FileQueryer() {
        initCursorFactories();
    }

    /**
     * 初始化游标工厂
     */
    private void initCursorFactories() {
        // 文件类型, 除目录类型外, 其它均为正数
        // 键为正值时, 非递归查询
        CURSOR_FACTORIES.put(IMAGE, new ImageCursorFactory(false));
        CURSOR_FACTORIES.put(AUDIO, new AudioCursorFactory(false));
        CURSOR_FACTORIES.put(VIDEO, new VideoCursorFactory(false));
        CURSOR_FACTORIES.put(DOC, new DocCursorFactory(false));
        CURSOR_FACTORIES.put(ZIP, new ZipCursorFactory(false));
        CURSOR_FACTORIES.put(APK, new ApkCursorFactory(false));
        CURSOR_FACTORIES.put(FILE, new FileCursorFactory(false));
        // 键为负值时, 递归查询
        CURSOR_FACTORIES.put(-IMAGE, new ImageCursorFactory(true));
        CURSOR_FACTORIES.put(-AUDIO, new AudioCursorFactory(true));
        CURSOR_FACTORIES.put(-VIDEO, new VideoCursorFactory(true));
        CURSOR_FACTORIES.put(-DOC, new DocCursorFactory(true));
        CURSOR_FACTORIES.put(-ZIP, new ZipCursorFactory(true));
        CURSOR_FACTORIES.put(-APK, new ApkCursorFactory(true));
        CURSOR_FACTORIES.put(-FILE, new FileCursorFactory(true));
    }


    @Override
    public void queryByMission(Context context, IFileQueryMisson mission) {
        // 文件类型, 除目录类型外均为正值
        int type = mission.type();
        // 获取游标工厂, 递归查询时, 键取负值, 获取的工厂即支持递归查询
        ICursorFactory factory = CURSOR_FACTORIES.get(mission.recursive() ? -type : type);
        // 执行查询
        CommonExecutor.get().execute(new QueryWork<>(context, factory, mission));
    }

}
