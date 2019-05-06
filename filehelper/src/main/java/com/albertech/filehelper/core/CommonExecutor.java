package com.albertech.filehelper.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 通用线程池, 执行异步任务
 */
public class CommonExecutor {

    private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();


    public static ExecutorService get() {
        return EXECUTOR;
    }

}
