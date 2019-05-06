package com.albertech.demo.func.hierarchy.mvp;

import android.content.Context;

import com.albertech.demo.func.hierarchy.HierarchyBean;
import com.albertech.demo.util.SortUtil;
import com.albertech.filehelper.api.IFileConstant;

import java.util.List;


public interface IHierarchyContract extends IFileConstant {


    interface IHierarchyModel {

        void init(IHierarchyPresenter presenter);

        void release();

        void query(Context context, String path);
    }


    interface IHierarchyView {

        void onResult(String path, List<HierarchyBean> list);
    }


    interface IHierarchyPresenter extends SortUtil.SortType {

        void init(Context context, IHierarchyView view);

        void release();

        void loadPath(String path);

        void onResult(String path, List<HierarchyBean> list);

        void showHidden(boolean show);

        void sortBy(int type);

        void refresh();

        boolean backToParent();
    }

}
