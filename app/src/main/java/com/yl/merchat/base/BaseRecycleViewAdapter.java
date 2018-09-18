package com.yl.merchat.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

/**
 * wrap recycle view adapter
 * <p>
 * Created by zm on 2018/9/10.
 */
public abstract class BaseRecycleViewAdapter<T> extends RecyclerView.Adapter {

    protected ArrayList<T> mDatas = new ArrayList<T>();

    public BaseRecycleViewAdapter() {

    }

    public BaseRecycleViewAdapter(ArrayList<T> datas) {
        mDatas = datas;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * set RecycleView Data
     *
     * @param datas  the datas
     * @param notify should notify adapter
     */
    public void addDatas(ArrayList<T> datas, boolean notify) {
        if (null == datas || datas.size() < 0) return;
        mDatas.addAll(datas);
        if (notify)
            notifyDataSetChanged();
    }

    /**
     * Add RecycleView Data
     *
     * @param datas  the datas
     * @param notify should notify adapter
     */
    public void setDatas(ArrayList<T> datas, boolean notify) {
        if (null == datas || datas.size() < 0) return;
        mDatas.clear();
        mDatas.addAll(datas);
        if (notify)
            notifyDataSetChanged();
    }

    /**
     * clear adapter data
     *
     * @param notify should notify adapter
     */
    public void clearData(boolean notify) {
        mDatas.clear();
        if (notify)
            notifyDataSetChanged();
    }

    public ArrayList<T> getDatas() {
        return mDatas;
    }

    public static interface RecycleViewItemListener {

        public void onItemClick(View view, int position);

        public void OnItemLongClickListener(View view, int position);
    }

    public static class SimpleRecycleViewItemListener implements RecycleViewItemListener {
        @Override
        public void onItemClick(View view, int position) {

        }

        @Override
        public void OnItemLongClickListener(View view, int position) {

        }
    }

    private RecycleViewItemListener mItemListener;

    public void setItemListener(RecycleViewItemListener listener) {
        mItemListener = listener;
    }

    /**
     * wrap recycle view  ViewHolder
     */
    public class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(View itemView) {
            super(itemView);
            if (null != mItemListener) {
                itemView.setOnClickListener(v -> {
                    mItemListener.onItemClick(itemView, getAdapterPosition());
                });
                itemView.setOnLongClickListener(v -> {
                    mItemListener.OnItemLongClickListener(itemView, getAdapterPosition());
                    return false;
                });
            }
        }
    }
}

