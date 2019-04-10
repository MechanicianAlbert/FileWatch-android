package com.albertech.demo.func.category.adapter;

import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.recycler.BaseHolder;
import com.albertech.demo.base.recycler.BaseRecyclerAdapter;
import com.albertech.demo.func.category.CategoryBean;
import com.albertech.demo.func.category.ICategoryContract;


public class CategoryAdapter extends BaseRecyclerAdapter<CategoryBean> {

    public CategoryAdapter() {
        updateData(ICategoryContract.CategoryModel.getCategories());
    }


    @Override
    public int getItemViewType(int position) {
        return R.layout.item_category;
    }

    @Override
    protected BaseHolder<BaseRecyclerAdapter<CategoryBean>, CategoryBean> getHolderByViewType(View itemView, int viewType) {
        return new CategoryHolder(this, itemView);
    }
}
