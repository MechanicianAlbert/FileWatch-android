package com.albertech.demo.func.category.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.albertech.common.base.recycler.normal.BaseRecyclerAdapter;
import com.albertech.common.base.recycler.normal.BaseHolder;
import com.albertech.demo.R;
import com.albertech.demo.func.category.CategoryBean;


public class CategoryHolder extends BaseHolder<BaseRecyclerAdapter<CategoryHolder, CategoryBean>, CategoryBean> {


    public CategoryHolder(BaseRecyclerAdapter<CategoryHolder, CategoryBean> adapter, @NonNull View itemView) {
        super(adapter, itemView);
    }

    @Override
    protected void onBind(int position, CategoryBean categoryBean) {
        ImageView iv = $(R.id.iv_item_category_icon);
        TextView tv = $(R.id.tv_item_category_name);

        iv.setImageResource(categoryBean.ICON);
        tv.setText(categoryBean.NAME);
    }

}
