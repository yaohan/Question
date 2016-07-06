package com.ssdut411.app.questionanswer.activity.system;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 *
 * CommonAdapter用到的ViewHolder
 * Created by yao_han on 2015/11/29.
 */
public class ViewHolder {
    private SparseArray<View> views;
    private View convertView;

    public View getConvertView() {
        return convertView;
    }

    public ViewHolder(ViewGroup parent, LayoutInflater layoutInflater, int layoutId) {
        this.views = new SparseArray<View>();
        this.convertView = layoutInflater.inflate(layoutId, parent, false);
        this.convertView.setTag(this);
    }

    public static ViewHolder getViewHolder(ViewGroup parent, View convertView, LayoutInflater layoutInflater, int layoutId) {
        if (convertView == null) {
            return new ViewHolder(parent, layoutInflater, layoutId);
        } else {
            return (ViewHolder) convertView.getTag();
        }
    }

    public <T extends View> T getView(int viewId){
        View view = views.get(viewId);
        if(view == null){
            view = convertView.findViewById(viewId);
            views.put(viewId,view);
        }
        return (T)view;
    }

    public TextView getTextView(int viewId){
        return getView(viewId);
    }

    public ImageView getImageView(int viewId){
        return getView(viewId);
    }

    public Button getButton(int viewId){
        return getView(viewId);
    }

    public EditText getEditText(int viewId){
        return getView(viewId);
    }

    public CheckBox getCheckBox(int viewId) { return getView(viewId); }
    public RadioButton getRadioButton(int viewId) { return getView(viewId); }

}
