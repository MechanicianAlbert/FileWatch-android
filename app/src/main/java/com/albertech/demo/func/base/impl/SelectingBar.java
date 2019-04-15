package com.albertech.demo.func.base.impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.albertech.demo.R;
import com.albertech.demo.func.base.select.ISelectContract;
import com.albertech.demo.util.Res;


public class SelectingBar extends FrameLayout implements ISelectContract.ISelectView {

    private ISelectContract.ISelectPresenter mPresenter;

    private TextView mTvSelectionCount;

    private boolean mHasSelectedAll;
    private CheckBox mBtnAllOrClear;


    public SelectingBar(@NonNull Context context) {
        this(context, null);
    }

    public SelectingBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectingBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rootView = inflater.inflate(R.layout.layout_selecting, this, false);
        addView(rootView);

        mTvSelectionCount = findViewById(R.id.tv_selection_count);

        findViewById(R.id.btn_stop_selecting).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPresenter != null) {
                    mPresenter.stopSelecting();
                }
            }
        });

        mBtnAllOrClear = findViewById(R.id.btn_all_or_clear);
        mBtnAllOrClear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    if (mPresenter != null) {
                        mPresenter.selectAll();
                    }
                } else {
                    if (mPresenter != null) {
                        mPresenter.clearSelection();
                    }
                }
            }
        });
    }

    private void setHasSelectedAll(boolean hasSelectedAll) {
        if (mHasSelectedAll ^ hasSelectedAll) {
            mHasSelectedAll = hasSelectedAll;
            if (mBtnAllOrClear != null) {
                mBtnAllOrClear.setChecked(mHasSelectedAll);
            }
        }
    }


    @Override
    public void onSelectingStatusChange(boolean isSelecting) {

    }

    @Override
    public void onSelectionCountChange(int count, boolean hasSelectedAll) {
        if (mTvSelectionCount != null) {
            mTvSelectionCount.setText(Res.string(R.string.str_selection_count, count));
        }
        setHasSelectedAll(hasSelectedAll);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mPresenter = null;
    }
}
