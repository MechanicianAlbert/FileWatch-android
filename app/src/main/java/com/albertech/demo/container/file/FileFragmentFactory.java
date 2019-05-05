package com.albertech.demo.container.file;

import com.albertech.common.base.fragment.BaseFragment;
import com.albertech.demo.func.apk.mvp.ApkViewFragment;
import com.albertech.demo.func.audio.mvp.AudioViewFragment;
import com.albertech.demo.func.doc.mvp.DocViewFragment;
import com.albertech.demo.func.image.mvp.ImageViewFragment;
import com.albertech.demo.func.video.mvp.VideoViewFragment;
import com.albertech.demo.func.zip.mvp.ZipViewFragment;
import com.albertech.filewatch.api.IFileConstant;


class FileFragmentFactory implements IFileConstant {


    static BaseFragment getCategoryFragmentInstance(int type) {
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
