package com.albertech.demo.base.recycler;

public interface OnItemClickListener<Bean> {

    boolean onItemClick(int position, Bean bean);

    boolean onItemLongClick(int position, Bean bean);
}
