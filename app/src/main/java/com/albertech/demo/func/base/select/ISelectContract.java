package com.albertech.demo.func.base.select;

public class ISelectContract {

    interface ISelectView {

        void bindPresenter(ISelectPresenter presenter);

        void onSelectionCountChange(int count, boolean hasSelectedAll);
    }

    interface ISelectPresenter {

        void selectAll();

        void clearSelection();

        void stopSelecting();
    }
}
