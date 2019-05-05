package com.albertech.demo.func.base.impl;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.albertech.demo.R;
import com.albertech.demo.func.base.select.ISelectContract;
import com.albertech.demo.util.Res;


public class TabSelectingBar extends Toolbar implements ISelectContract.ISelectView {

    private final OnMenuItemClickListener MENU_ITEM_CLICK_LISTENER = new OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.action_select_all) {
                if (mModel != null) {
                    mModel.selectAll();
                }
            } else if (id == R.id.action_clear_selection) {
                if (mModel != null) {
                    mModel.clearSelection();
                }
            } else if (id == R.id.action_sort_by) {
                Toast.makeText(getContext(), "Sort by", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.action_new_folder) {
                Toast.makeText(getContext(), "New folder", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.action_show_hidden) {
                Toast.makeText(getContext(), "Show hidden", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    };
    private final OnClickListener NAVIGATION_CLICK_LISTENER = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mIsSelecting) {
                if (mModel != null) {
                    mModel.stopSelecting();
                }
            } else {
                Context context = getContext();
                if (context instanceof Activity) {
                    ((Activity) context).onBackPressed();
                }
            }
        }
    };
    private final TabLayout.OnTabSelectedListener TAB_SELECT_CHANGE_LISTENER = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            if (mModel != null) {
                mModel.stopSelecting();
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };


    private ISelectContract.ISelectModel mModel;

    private TextView mTvSelectionCount;
    private TabLayout mTl;

    private boolean mIsSelecting;
    private boolean mHasSelectedAll;


    public TabSelectingBar(Context context) {
        super(context);
    }

    public TabSelectingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabSelectingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }


    @Override
    public void bindModel(ISelectContract.ISelectModel model) {
        mModel = model;
    }


    public void bindViewPager(ViewPager viewPager) {
        if (mTl != null) {
            mTl.setupWithViewPager(viewPager, true);
        }
    }


    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.toolbar_home, this, true);

        setBackgroundResource(R.color.colorGrayMain);

        mTl = findViewById(R.id.tl_home);
        mTvSelectionCount = findViewById(R.id.tv_bar_selection_count);

        setContentInsetStartWithNavigation(0);
        setOverflowIcon(Res.drawable(R.drawable.ic_bar_menu));

        mTl.addOnTabSelectedListener(TAB_SELECT_CHANGE_LISTENER);
        setNavigationOnClickListener(NAVIGATION_CLICK_LISTENER);

        updateStatus();
    }

    private void updateStatus() {
        if (mIsSelecting) {
            mTl.setVisibility(GONE);
            mTvSelectionCount.setVisibility(VISIBLE);
            setNavigationIcon(R.drawable.ic_bar_end);
        } else {
            mTl.setVisibility(VISIBLE);
            mTvSelectionCount.setVisibility(GONE);
            setNavigationIcon(R.drawable.ic_bar_back);
        }
        updateMenu();
        invalidate();
    }

    private void updateMenu() {
        Menu menu = getMenu();
        if (menu != null) {
            menu.clear();
        }
        if (mIsSelecting) {
            if (mHasSelectedAll) {
                inflateMenu(R.menu.menu_clear_selection);
            } else {
                inflateMenu(R.menu.menu_select_all);
            }
        } else {
            inflateMenu(R.menu.menu_normal);
        }
        setOnMenuItemClickListener(MENU_ITEM_CLICK_LISTENER );
    }

    private void setHasSelectedAll(boolean hasSelectedAll) {
        if (mHasSelectedAll ^ hasSelectedAll) {
            mHasSelectedAll = hasSelectedAll;
            updateMenu();
            invalidate();
        }
    }


    @Override
    public void onSelectingStatusChange(boolean isSelecting) {
        mIsSelecting = isSelecting;
        updateStatus();
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
        mModel = null;
    }

}
