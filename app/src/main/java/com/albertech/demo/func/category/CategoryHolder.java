package com.albertech.demo.func.category;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.albertech.demo.R;
import com.albertech.demo.base.recycler.BaseHolder;
import com.albertech.demo.base.recycler.BaseRecyclerAdapter;



public class CategoryHolder extends BaseHolder<BaseRecyclerAdapter<CategoryBean>, CategoryBean> {


    public CategoryHolder(BaseRecyclerAdapter<CategoryBean> adapter, @NonNull View itemView) {
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
