package com.albertech.demo.func.category;

import com.albertech.demo.R;
import com.albertech.demo.util.Res;

import java.util.ArrayList;
import java.util.List;


public interface ICategoryContract {

    class CategoryModel {

        private static final List<CategoryBean> CATEGORIES = new ArrayList<>();

        static {
            CATEGORIES.add(new CategoryBean(Res.string(R.string.str_category_image), R.drawable.ic_type_image));
            CATEGORIES.add(new CategoryBean(Res.string(R.string.str_category_audio), R.drawable.ic_type_audio));
            CATEGORIES.add(new CategoryBean(Res.string(R.string.str_category_video), R.drawable.ic_type_video));
            CATEGORIES.add(new CategoryBean(Res.string(R.string.str_category_doc), R.drawable.ic_type_doc));
            CATEGORIES.add(new CategoryBean(Res.string(R.string.str_category_apk), R.drawable.ic_type_apk));
            CATEGORIES.add(new CategoryBean(Res.string(R.string.str_category_zip), R.drawable.ic_type_zip));
            CATEGORIES.add(new CategoryBean(Res.string(R.string.str_category_download), R.drawable.ic_type_download));
        }

        public static List<CategoryBean> getCategories() {
            return new ArrayList<>(CATEGORIES);
        }
    }
}
