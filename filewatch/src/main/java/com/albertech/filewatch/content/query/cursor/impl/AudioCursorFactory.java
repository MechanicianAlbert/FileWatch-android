package com.albertech.filewatch.content.query.cursor.impl;

import android.net.Uri;
import android.provider.MediaStore;

import com.albertech.filewatch.content.query.cursor.AbsCursorFactory;


public class AudioCursorFactory extends AbsCursorFactory {

    public AudioCursorFactory() {

    }

    public AudioCursorFactory(String[] projection) {
        super(projection);
    }


    @Override
    public String getPathColumnName() {
        return MediaStore.Audio.Media.DATA;
    }

    @Override
    protected Uri uri() {
        return MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    }
}
