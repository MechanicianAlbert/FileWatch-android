package com.albertech.filewatch.watch;

import com.albertech.filewatch.api.IFileWatchSubscriber;



public interface IFileWatchDispatch {

    void subscribeFileWatch(IFileWatchSubscriber subscriber, String path);

    void unsubscribeFileWatch(IFileWatchSubscriber subscriber);
}
