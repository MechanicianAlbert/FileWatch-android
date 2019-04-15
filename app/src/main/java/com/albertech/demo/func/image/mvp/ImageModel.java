package com.albertech.demo.func.image.mvp;


import android.database.Cursor;

import com.albertech.demo.R;
import com.albertech.demo.func.base.query.impl.BaseQueryModel;
import com.albertech.demo.func.image.ImageBean;


public class ImageModel extends BaseQueryModel<ImageBean> {


    @Override
    public final int type() {
        return IMAGE;
    }

    @Override
    public boolean recursive() {
        return true;
    }

    @Override
    public final String[] projection() {
        return new String[]{COLUMN_NAME_PATH};
    }

    @Override
    public ImageBean parse(Cursor cursor) {
        ImageBean bean = super.parse(cursor);
        bean.type = IMAGE;
        bean.icon = R.drawable.ic_type_image;
        return bean;
    }

    @Override
    protected ImageBean createFileBean() {
        return new ImageBean();
    }

}
