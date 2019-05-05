package com.albertech.filewatch.core.query;

import android.content.Context;
import android.util.SparseArray;

import com.albertech.filewatch.api.IFileQueryMisson;
import com.albertech.filewatch.core.query.cursor.ICursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.direct.DApkCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.direct.DAudioCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.direct.DDocCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.direct.DFileCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.direct.DImageCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.direct.DVideoCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.direct.DZipCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.recursive.RApkCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.recursive.RAudioCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.recursive.RDocCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.recursive.RFileCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.recursive.RImageCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.recursive.RVideoCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.recursive.RZipCursorFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class FileQueryer implements IFileQuery {

    /**
     * 游标工厂集合
     */
    private static final SparseArray<ICursorFactory> CURSOR_FACTORIES = new SparseArray<>();
    /**
     * 执行查询任务线程池
     */
    private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();


    public FileQueryer() {
        initCursorFactories();
    }

    /**
     * 初始化游标工厂
     */
    private void initCursorFactories() {
        CURSOR_FACTORIES.put(IMAGE, new DImageCursorFactory());
        CURSOR_FACTORIES.put(AUDIO, new DAudioCursorFactory());
        CURSOR_FACTORIES.put(VIDEO, new DVideoCursorFactory());
        CURSOR_FACTORIES.put(DOC, new DDocCursorFactory());
        CURSOR_FACTORIES.put(ZIP, new DZipCursorFactory());
        CURSOR_FACTORIES.put(APK, new DApkCursorFactory());
        CURSOR_FACTORIES.put(FILE, new DFileCursorFactory());

        CURSOR_FACTORIES.put(-IMAGE, new RImageCursorFactory());
        CURSOR_FACTORIES.put(-AUDIO, new RAudioCursorFactory());
        CURSOR_FACTORIES.put(-VIDEO, new RVideoCursorFactory());
        CURSOR_FACTORIES.put(-DOC, new RDocCursorFactory());
        CURSOR_FACTORIES.put(-ZIP, new RZipCursorFactory());
        CURSOR_FACTORIES.put(-APK, new RApkCursorFactory());
        CURSOR_FACTORIES.put(-FILE, new RFileCursorFactory());
    }


    @Override
    public void queryByMission(Context context, IFileQueryMisson mission) {
        int type = mission.type();
        // 获取游标工厂
        ICursorFactory factory = CURSOR_FACTORIES.get(mission.recursive() ? -type : type);
        // 执行查询
        EXECUTOR.execute(new QueryWork<>(context, factory, mission));
    }

}
