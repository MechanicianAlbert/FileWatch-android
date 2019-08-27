package com.albertech.filehelper.core.scan.commit;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.albertech.filehelper.core.dispatch.FileWatchService;

/**
 * 用于提交扫描任务的主服务的连接类
 * 使外部可以主动向主服务内的扫描器提交系统扫描媒体库的任务
 *
 * 此类模仿系统MediaScannerConnection的实现方式, 完全可以采用其替代此类
 * 不使用系统原生的提交方式, 是为了不干扰主扫描器任务队列的执行, 并使扫描结果自动通过分发器分发
 */
public class ScanCommitter implements ServiceConnection {

    private static final String TAG = ScanCommitter.class.getSimpleName();

    private Context mContext;
    private IScanCommit mCommitter;
    private String[] mPaths;
    private volatile boolean mHadUnboundService;


    public ScanCommitter(Context context, String...paths) {
        mContext = context;
        mPaths = paths;
        mContext.bindService(createFileWatchServiceIntent(), this, Context.BIND_AUTO_CREATE);
    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        if (service instanceof IScanCommit) {
            // 主服务返回的Binder实例即为主服务持有的分发器, 全局唯一, 强转该接口即可向其进行订阅/反订阅
            mCommitter = (IScanCommit) service;
            mCommitter.commitScanMission(mPaths);
            // 提交扫描任务后释放, 以便被自动回收空间
            release();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        // 与服务断开连接 可能并非由于主动调用release();
        // 此时应再次调用release(), 保证release()至少得到1次执行;
        // release()是幂等方法, 多次调用不会导致异常, 也不会改变状态
        release();
    }

    /**
     * @return 绑定主服务的意图实例
     */
    private Intent createFileWatchServiceIntent() {
        return new Intent(mContext, FileWatchService.class);
    }

    /**
     * 释放
     */
    private void release() {
        if (!mHadUnboundService) {
            mHadUnboundService = true;
            // 反订阅时断开与服务的绑定, 防止重复解绑, 加异常检查
            try {
                mContext.unbindService(this);
            } catch (IllegalArgumentException e) {
                // ignore
            }
        }
        // 释放由服务端获取的Binder
        mCommitter = null;
    }

}
