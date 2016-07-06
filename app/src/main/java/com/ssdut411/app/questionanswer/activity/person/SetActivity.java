package com.ssdut411.app.questionanswer.activity.person;

import android.view.View;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.system.BaseActivity;
import com.ssdut411.app.questionanswer.application.MainApplication;
import com.ssdut411.app.questionanswer.utils.T;

/**
 * Created by yao_han on 2016/3/23.
 */
public class SetActivity extends BaseActivity {
    @Override
    protected String initTitle() {
        return "个人设置";
    }

    @Override
    protected int initMenu() {
        return 0;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_set;
    }

    @Override
    protected void initViews() {
        getTextView(R.id.tv_set_change_pwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.showShort(context,"修改密码");
            }
        });

        getTextView(R.id.tv_set_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainApplication.getInstance().setLogin(false);
                finish();
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void showView() {
        setCanBack();
    }
}
