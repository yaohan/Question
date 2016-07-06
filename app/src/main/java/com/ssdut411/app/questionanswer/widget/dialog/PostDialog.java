package com.ssdut411.app.questionanswer.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ssdut411.app.questionanswer.R;

/**
 * Created by yao_han on 2016/4/18.
 */
public class PostDialog extends Dialog {
    private static TextView tvStudent;
    public PostDialog(Context context, int theme) {
        super(context, theme);
    }

    public void setStudent(String student){
        tvStudent.setText(student);
    };

    public static class Builder{
        protected Context context;
        protected int layoutId;
        protected View layout;

        private TextView tvName,tvCount,tvPoint;
        private TextView tvTitle,tvDesc,btSure,btCancel;
        private PostDialog dialog;

        public Builder(Context context, int layoutId) {
            this.context = context;
            this.layoutId = layoutId;
            dialog = create();
        }
        public PostDialog getDialog() {
            return dialog;
        }

        private PostDialog create(){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = layoutInflater.inflate(layoutId,null);
            dialog = new PostDialog(context,R.style.Dialog);
            dialog.setContentView(layout);
            setView();
            return dialog;
        }

        private void setView() {
            tvTitle = (TextView)layout.findViewById(R.id.tv_dialog_title);
            tvDesc = (TextView)layout.findViewById(R.id.tv_dialog_desc);
            btSure = (TextView)layout.findViewById(R.id.bt_dialog_sure);
            btCancel = (TextView)layout.findViewById(R.id.bt_dialog_cancel);
            tvName = ((TextView) layout.findViewById(R.id.tv_dialog_name));
            tvStudent = ((TextView) layout.findViewById(R.id.tv_dialog_student));
            tvCount = ((TextView) layout.findViewById(R.id.tv_dialog_count));
            tvPoint = ((TextView) layout.findViewById(R.id.tv_dialog_point));
        }

        public Builder setHomeWorkName(String name){
            tvName.setText(name);
            return this;
        }
        public Builder setCount(String count){
            tvCount.setText(count);
            return this;
        }
        public Builder setPoint(String point){
            tvPoint.setText(point);
            return this;
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
