package com.albertech.filewatch.core.query.cursor.impl;

import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.albertech.filewatch.core.query.cursor.AbsCursorFactory;


public class DocCursorFactory extends AbsCursorFactory {

    private final String[] DOC_TYPES = new String[]{
            "%",
            "%.pdf",
            "%.xml",
            "%.html",
            "%.asm",
            "%.text/x-asm",
            "%.def",
            "%.in",
            "%.rc",
            "%.list",
            "%.log",
            "%.pl",
            "%.prop",
            "%.properties",
            "%.rc",
            "%.doc",
            "%.docx",
            "%.msg",
            "%.odt",
            "%.pages",
            "%.rtf",
            "%.txt",
            "%.wpd",
            "%.wps"
    };

    public DocCursorFactory() {

    }

    public DocCursorFactory(String[] projection) {
        super(projection);
    }


    @Override
    public String getPathColumnName() {
        return MediaStore.Files.FileColumns.DATA;
    }


    @Override
    protected Uri uri() {
        return MediaStore.Files.getContentUri("external");
    }

    @Override
    protected String createSelectionByPath(String path) {
        String selection = null;
        if (!TextUtils.isEmpty(path)) {
            StringBuffer b = new StringBuffer("");
            b.append(MediaStore.Files.FileColumns.DATA).append(" LIKE ? AND ");
            for (int i = 0; i < DOC_TYPES.length - 2; i++) {
                b.append(MediaStore.Files.FileColumns.DATA).append(" LIKE ? OR ");
            }
            b.append(MediaStore.Files.FileColumns.DATA).append(" LIKE ? ");
            selection = b.toString();
        }
        return selection;
    }

    @Override
    protected String[] createSelectionArgsByPath(String path) {
        if (!TextUtils.isEmpty(path)) {
            DOC_TYPES[0] = path + "%";
        }
        return DOC_TYPES;
    }
}
