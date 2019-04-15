package com.albertech.demo.func.image.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.albertech.demo.R;
import com.albertech.demo.base.recycler.selectable.SelectableHolder;
import com.albertech.demo.base.recycler.selectable.SelectableRecyclerAdapter;
import com.albertech.demo.func.image.ImageBean;
import com.bumptech.glide.Glide;

import java.io.File;


public class ImageHolder extends SelectableHolder<SelectableRecyclerAdapter<ImageHolder, ImageBean>, ImageBean> {


    public ImageHolder(SelectableRecyclerAdapter<ImageHolder, ImageBean> adapter, @NonNull View itemView) {
        super(adapter, itemView);
    }

    @Override
    protected void onBind(int position, ImageBean imageBean) {
        ImageView iv = $(R.id.iv_item_image);
        Glide.with(getContext())
                .load(Uri.fromFile(new File(imageBean.path)))
                .into(iv);
    }
}
