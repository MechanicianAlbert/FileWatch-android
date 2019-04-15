package com.albertech.demo.func.base.select;

import com.albertech.demo.base.recycler.selectable.ISelectableAdapter;
import com.albertech.demo.base.recycler.selectable.ISelectionListener;

import java.util.List;

public interface ISelectContract {

    interface ISelectModel<Bean> extends ISelectableAdapter<Bean> {

    }


    interface ISelectView extends ISelectionListener {

    }


    interface ISelectPresenter {

        void stopSelecting();

        void selectAll();

        void clearSelection();
    }
}
