package com.ssdut411.app.questionanswer.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssdut411.app.questionanswer.R;


/**
 * Created by yao_han on 2015/11/30.
 */
public class BaseDialog extends Dialog{

    public BaseDialog(Context context, int theme) {
        super(context,theme);
    }

    public abstract static class Builder{
        protected Context context;
        protected int layoutId;
        protected View layout;

        protected BaseDialog dialog;

        public Builder(Context context, int layoutId){
            this.context = context;
            this.layoutId = layoutId;
            dialog = create();
        }

        public BaseDialog getDialog() {
            return dialog;
        }

        private BaseDialog create(){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = layoutInflater.inflate(layoutId,null);
            dialog = new BaseDialog(context,R.style.Dialog);
            dialog.setContentView(layout);
            setView();
            return dialog;
        }

        protected abstract void setView();



    }
}
