package com.albertech.demo.container.category;

import com.albertech.demo.base.bean.BaseFileBean;
import com.albertech.demo.base.recycler.selectable.SelectableHolder;
import com.albertech.demo.base.recycler.selectable.SelectableRecyclerAdapter;
import com.albertech.demo.func.apk.mvp.ApkViewFragment;
import com.albertech.demo.func.audio.mvp.AudioViewFragment;
import com.albertech.demo.func.base.impl.BaseFileViewFragment;
import com.albertech.demo.func.base.impl.BaseSelectionAdapter;
import com.albertech.demo.func.doc.mvp.DocViewFragment;
import com.albertech.demo.func.image.mvp.ImageViewFragment;
import com.albertech.demo.func.video.mvp.VideoViewFragment;
import com.albertech.demo.func.zip.mvp.ZipViewFragment;
import com.albertech.filewatch.api.IFileConstant;

class CategoryFragmentFactory implements IFileConstant {


    static BaseFileViewFragment<? extends BaseSelectionAdapter<? extends SelectableHolder<? extends SelectableRecyclerAdapter<? extends SelectableHolder<?,?>,? extends BaseFileBean>,? extends BaseFileBean>,? extends BaseFileBean>, ? extends BaseFileBean> getCategoryFragmentInstance(int type) {
        switch (type) {
            case IMAGE:
                return new ImageViewFragment();
            case AUDIO:
                return new AudioViewFragment();
            case VIDEO:
                return new VideoViewFragment();
            case DOC:
                return new DocViewFragment();
            case APK:
                return new ApkViewFragment();
            case ZIP:
                return new ZipViewFragment();
            default:
                return null;
        }
    }

}
