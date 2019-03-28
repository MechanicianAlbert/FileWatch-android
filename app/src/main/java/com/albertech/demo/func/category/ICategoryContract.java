package com.albertech.demo.func.category;

import android.content.Context;

import com.albertech.demo.R;
import com.albertech.demo.util.Res;

import java.util.ArrayList;
import java.util.List;

public interface ICategoryContract {

    class CategoryModel {

        final List<CategoryBean> CATEGORIES = new ArrayList<>();

        CategoryModel(Context context) {
            CATEGORIES.add(new CategoryBean(Res.string(context, R.string.str_category_image), R.drawable.ic_launcher));
            CATEGORIES.add(new CategoryBean(Res.string(context, R.string.str_category_audio), R.drawable.ic_launcher));
            CATEGORIES.add(new CategoryBean(Res.string(context, R.string.str_category_video), R.drawable.ic_launcher));
            CATEGORIES.add(new CategoryBean(Res.string(context, R.string.str_category_doc), R.drawable.ic_launcher));
            CATEGORIES.add(new CategoryBean(Res.string(context, R.string.str_category_apk), R.drawable.ic_launcher));
            CATEGORIES.add(new CategoryBean(Res.string(context, R.string.str_category_zip), R.drawable.ic_launcher));
            CATEGORIES.add(new CategoryBean(Res.string(context, R.string.str_category_download), R.drawable.ic_launcher));
        }
    }
}
