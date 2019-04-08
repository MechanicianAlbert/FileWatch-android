package com.albertech.demo.func.hierarchy.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.recycler.BaseHolder;
import com.albertech.demo.base.recycler.BaseRecyclerAdapter;
import com.albertech.demo.func.hierarchy.HierarchyBean;
import com.albertech.demo.util.DateUtil;
import com.albertech.demo.util.SizeUtil;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class HierarchyHolder extends BaseHolder<BaseRecyclerAdapter<HierarchyBean>, HierarchyBean> {


    public HierarchyHolder(BaseRecyclerAdapter<HierarchyBean> adapter, @NonNull View itemView) {
        super(adapter, itemView);
    }

    @Override
    protected void onBind(int position, HierarchyBean hierarchyBean) {
        setText(R.id.tv_item_hierarchy_name, hierarchyBean.name);
        setText(R.id.tv_item_hierarchy_size, SizeUtil.format(hierarchyBean.size));
        setText(R.id.tv_item_hierarchy_date, DateUtil.format(hierarchyBean.date));
        setImage(R.id.iv_item_hierarchy_icon, hierarchyBean.icon);
    }
}
