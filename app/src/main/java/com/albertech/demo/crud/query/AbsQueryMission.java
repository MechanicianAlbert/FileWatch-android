package com.albertech.demo.crud.query;

import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.albertech.demo.R;
import com.albertech.demo.base.bean.BaseFileBean;
import com.albertech.demo.crud.query.hierarchy.HierarchyBean;
import com.albertech.demo.util.ForEachUtil;
import com.albertech.demo.util.SizeUtil;
import com.albertech.filewatch.api.FileHelper;
import com.albertech.filewatch.api.IFileConstant;
import com.albertech.filewatch.api.IFileQueryMisson;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


public abstract class AbsQueryMission<Bean extends BaseFileBean> implements IFileQueryMisson<Bean> {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINA);

    private static final String[] BASE_PROJECTION = new String[]{
            COLUMN_NAME_PATH,
            COLUMN_NAME_MEDIA_TYPE
    };

    private static final int SORT_BY_ALPHABET = 0;
    private static final int SORT_BY_DATE = 1;

    private static final Comparator<BaseFileBean> COMPARATOR_ALPHABET = new Comparator<BaseFileBean>() {
        @Override
        public int compare(BaseFileBean o1, BaseFileBean o2) {
            return o1.name.compareTo(o2.name);
        }
    };

    private static final Comparator<BaseFileBean> COMPARATOR_DATE = new Comparator<BaseFileBean>() {
        @Override
        public int compare(BaseFileBean o1, BaseFileBean o2) {
            return Long.compare(o1.date, o2.date);
        }
    };


    @Override
    public String[] projection() {
        String[] added = addProjection();
        if (added != null && added.length > 0) {
            int length = added.length;
            String[] projection = new String[BASE_PROJECTION.length + length];
            System.arraycopy(BASE_PROJECTION, 0, projection, 0, BASE_PROJECTION.length);
            System.arraycopy(added, 0, projection, BASE_PROJECTION.length - 1, length);
            return projection;
        } else {
            return BASE_PROJECTION;
        }
    }

    @Override
    public String parentPath() {
        return null;
    }

    @Override
    public boolean recursive() {
        return false;
    }

    @Override
    public Bean parse(Cursor cursor) {
        Bean bean = createFileBean();
        bean.path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
        bean.file = new File(bean.path);
        bean.isDirectory = bean.file.isDirectory();
        bean.name = bean.file.getName();
        bean.size = bean.file.length();
        bean.date = bean.file.lastModified();
        bean.parent = bean.file.getParent();
        bean.isHidden = bean.file.isHidden();
        boolean hasSuffix = bean.name.contains(".");
        if (hasSuffix) {
            bean.suffix = bean.name.substring(bean.name.lastIndexOf("."));
        } else {
            bean.suffix = "";
        }
        int type = cursor.getInt(cursor.getColumnIndex(MediaStore.Files.FileColumns.MEDIA_TYPE));
        if (bean.isDirectory) {
            bean.type = DIRECTORY;
            bean.icon = R.drawable.ic_launcher;
        } else if (type == MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE) {
            bean.type = IMAGE;
            bean.icon = R.drawable.ic_type_image;
        } else if (type == MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO) {
            bean.type = AUDIO;
            bean.icon = R.drawable.ic_type_audio;
        } else if (type == MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO) {
            bean.type = VIDEO;
            bean.icon = R.drawable.ic_type_video;
        } else if (Arrays.asList(IFileConstant.DOC_SUFFIX).contains(bean.suffix)) {
            bean.type = DOC;
            bean.icon = R.drawable.ic_type_doc;
        } else if (Arrays.asList(IFileConstant.APK_SUFFIX).contains(bean.suffix)) {
            bean.type = APK;
            bean.icon = R.drawable.ic_type_apk;
        } else if (Arrays.asList(IFileConstant.ZIP_SUFFIX).contains(bean.suffix)) {
            bean.type = ZIP;
            bean.icon = R.drawable.ic_type_zip;
        } else {
            bean.type = UNKNOWN;
        }
        return bean;
    }

    @Override
    public void onQueryResult(String path, List list) {
        if (!showHidden()) {
            Iterator<BaseFileBean> i = list.iterator();
            while (i.hasNext()) {
                BaseFileBean b = i.next();
                if (b.isHidden) {
                    i.remove();
                }
            }
        }

        int sortBy = sortBy();
        Comparator<BaseFileBean> comparator;
        if (sortBy == 0) {
            comparator = COMPARATOR_ALPHABET;
        } else {
            comparator = COMPARATOR_DATE;
        }
        Collections.sort(list, comparator);


        ForEachUtil.forEach(list, new ForEachUtil.ItemHandler<HierarchyBean>() {
            @Override
            public void handle(HierarchyBean bean) {
                String msg = "File details:\n"
                        + "Path: " + bean.path + "\n"
                        + "Name: " + bean.name + "\n"
                        + "Size: " + SizeUtil.format(bean.size) + "\n"
                        + "Date: " + FORMAT.format(bean.date) + "\n"
                        + "Parent: " + bean.parent + "\n"
                        + "Suffix: " + bean.suffix + "\n"
                        + "Type: " + FileHelper.fileTypeName(bean.type) + "\n";
                Log.i("AAA", msg);
            }
        });
    }


    protected String[] addProjection() {
        return null;
    }

    protected boolean showHidden() {
        return false;
    }

    protected int sortBy() {
        return SORT_BY_ALPHABET;
    }

    protected abstract Bean createFileBean();
}
