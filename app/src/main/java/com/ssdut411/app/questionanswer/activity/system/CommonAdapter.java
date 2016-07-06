package com.ssdut411.app.questionanswer.activity.system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 *
 * 通用的Adapter
 * Created by yao_han on 2015/11/29.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    private List<T> list;
    private LayoutInflater layoutInflater;
    private int layoutId;

    public CommonAdapter(Context context, List<T> list, int layoutId) {
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.getViewHolder(parent, convertView, layoutInflater, layoutId);
        convert(viewHolder, list.get(position),position);
        return viewHolder.getConvertView();
    }

    public abstract void convert(ViewHolder viewHolder,T t, int position);
}

