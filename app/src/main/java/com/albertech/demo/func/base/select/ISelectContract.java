package com.albertech.demo.func.base.select;

public interface ISelectContract {

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
