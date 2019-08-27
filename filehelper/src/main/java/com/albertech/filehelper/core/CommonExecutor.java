package com.albertech.filehelper.core;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 通用线程池, 执行异步任务
 */
public class CommonExecutor {

    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(8);


    /**
     * 获取调度器, 可作为线程池或定时器
     * @return 调度器
     */
    public static ScheduledExecutorService get() {
        return SCHEDULER;
    }

    /**
     * 执行异步任务
     * @param r 任务
     */
    public static void exe(Runnable r) {
        SCHEDULER.execute(r);
    }

    /**
     * 延迟执行1次异步任务
     * @param r 任务
     * @param delay 延迟时间 (单位: 毫秒)
     * @return 延迟任务的Future对象, 可用于取消任务
     */
    public static ScheduledFuture schedule(Runnable r, long delay) {
        return SCHEDULER.schedule(r, delay, TimeUnit.MILLISECONDS);
    }

}
