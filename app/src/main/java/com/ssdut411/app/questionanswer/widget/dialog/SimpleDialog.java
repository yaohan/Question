package com.ssdut411.app.questionanswer.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ssdut411.app.questionanswer.R;

/**
 * Created by yao_han on 2016/4/18.
 */
public class SimpleDialog extends BaseDialog {


    public SimpleDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder extends BaseDialog.Builder{
        private TextView tvTitle;
        private TextView tvDesc;
        private TextView btSure;
        private TextView btCancel;

        public Builder(Context context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        protected void setView() {
            tvTitle = (TextView)layout.findViewById(R.id.tv_dialog_title);
            tvDesc = (TextView)layout.findViewById(R.id.tv_dialog_desc);
            btSure = (TextView)layout.findViewById(R.id.bt_dialog_sure);
            btCancel = (TextView)layout.findViewById(R.id.bt_dialog_cancel);
        }

        public Builder setTitle(String title){
            tvTitle.setText(title);
            return this;
        }
        public Builder setDesc(String desc){
            tvDesc.setText(desc);
            return this;
        }
        public Builder setPositiveButton(String sure, View.OnClickListener listener){
            if(sure.equals("")){
                btSure.setVisibility(View.GONE);
            }else{
                btSure.setText(sure);
                btSure.setOnClickListener(listener);
            }
            return this;
        }
        public Builder setNegativeButton(String cancel, View.OnClickListener listener){
            if(cancel.equals("")){
                btCancel.setVisibility(View.GONE);
            }else{
                btCancel.setText(cancel);
                btCancel.setOnClickListener(listener);
            }
            return this;
        }
    }
}
