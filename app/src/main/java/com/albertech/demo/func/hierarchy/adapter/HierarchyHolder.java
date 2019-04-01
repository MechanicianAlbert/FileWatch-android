package com.albertech.demo.func.hierarchy.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.albertech.demo.R;
import com.albertech.demo.base.recycler.BaseHolder;
import com.albertech.demo.base.recycler.BaseRecyclerAdapter;
import com.albertech.demo.crud.query.hierarchy.HierarchyBean;
import com.albertech.demo.util.SizeUtil;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class HierarchyHolder extends BaseHolder<BaseRecyclerAdapter<HierarchyBean>, HierarchyBean> {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss", Locale.CHINA);

    public HierarchyHolder(BaseRecyclerAdapter<HierarchyBean> adapter, @NonNull View itemView) {
        super(adapter, itemView);
    }

    @Override
    protected void onBind(int position, HierarchyBean hierarchyBean) {
        setText(R.id.tv_item_hierarchy_name, hierarchyBean.name);
        setText(R.id.tv_item_hierarchy_size, SizeUtil.format(hierarchyBean.size));
        setText(R.id.tv_item_hierarchy_date, FORMAT.format(hierarchyBean.date));
    }
}
