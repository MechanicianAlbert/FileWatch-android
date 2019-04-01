package com.albertech.demo.crud.query.hierarchy;


import com.albertech.demo.crud.query.AbsQueryMission;



public class HierarchyQueryMission extends AbsQueryMission<HierarchyBean> {

    @Override
    public int type() {
        return FILE;
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
    protected HierarchyBean createFileBean() {
        return new HierarchyBean();
    }

}
