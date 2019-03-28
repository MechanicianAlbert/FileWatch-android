package com.albertech.demo.func.category;

import android.content.Context;
import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.recycler.BaseHolder;
import com.albertech.demo.base.recycler.BaseRecyclerAdapter;



public class CategoryAdapter extends BaseRecyclerAdapter<CategoryBean> {

    CategoryAdapter(Context context) {
        updateData(new ICategoryContract.CategoryModel(context).CATEGORIES);
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
