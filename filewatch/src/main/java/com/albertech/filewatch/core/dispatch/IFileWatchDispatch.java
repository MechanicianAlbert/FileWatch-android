package com.albertech.filewatch.core.dispatch;

import com.albertech.filewatch.api.IFileWatchSubscriber;


public interface IFileWatchDispatch {

    void subscribeFileWatch(IFileWatchSubscriber subscriber, int type, String path);

    void unsubscribeFileWatch(IFileWatchSubscriber subscriber);

}
