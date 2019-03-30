package com.albertech.demo.func.image.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.albertech.demo.R;
import com.albertech.demo.base.recycler.BaseHolder;
import com.albertech.demo.base.recycler.BaseRecyclerAdapter;
import com.albertech.demo.crud.query.image.ImageBean;
import com.bumptech.glide.Glide;

import java.io.File;


public class ImageHolder extends BaseHolder<BaseRecyclerAdapter<ImageBean>, ImageBean> {


    public ImageHolder(BaseRecyclerAdapter<ImageBean> adapter, @NonNull View itemView) {
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
