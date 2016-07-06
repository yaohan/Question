package com.ssdut411.app.questionanswer.activity.person;

import android.content.Intent;
import android.view.View;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.student.MainPageActivity;
import com.ssdut411.app.questionanswer.activity.system.BaseActivity;
import com.ssdut411.app.questionanswer.application.MainApplication;
import com.ssdut411.app.questionanswer.model.Resp.LoginResp;
import com.ssdut411.app.questionanswer.model.model.ModelConfig;

/**
 * Created by yao_han on 2015/12/1.
 */
public class SelectRoleActivity extends BaseActivity {
    private String target;

    @Override
    protected String initTitle() {
        return getString(R.string.change_role);
    }

    @Override
    protected int initMenu() {
        return R.menu.menu_null;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_select_role;
    }

    @Override
    protected void initViews() {
        target = getIntent().getStringExtra("target");
        getLinearLayout(R.id.ll_select_student).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (target!=null && target.equals("register")) {
                    MainApplication.getInstance().setRole(MainApplication.ROLE_PUPILS);
                    startInfoActivity(target, ModelConfig.ROLE_PUPILS);
                } else {
                    Intent intent = new Intent(context, MainPageActivity.class);
                    startActivity(intent);
                }
            }
        });
        getLinearLayout(R.id.ll_select_teacher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (target!=null && target.equals("register")) {
                    MainApplication.getInstance().setRole(MainApplication.ROLE_TEACHER);
                    startInfoActivity(target, ModelConfig.ROLE_TEACHER);
                } else {
                    Intent intent = new Intent(context, com.ssdut411.app.questionanswer.activity.teacher.MainPageActivity.class);
                    startActivity(intent);
                }
            }
        });
        getLinearLayout(R.id.ll_select_parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (target!=null && target.equals("register")) {
                    MainApplication.getInstance().setRole(MainApplication.ROLE_PARENT);
                    startInfoActivity(target, ModelConfig.ROLE_PARENT);
                } else {
                    Intent intent = new Intent(context, com.ssdut411.app.questionanswer.activity.parent.MainPageActivity.class);
                    startActivity(intent);
                }
            }
        });
        ;
    }

    private void startInfoActivity(String target, int role) {
        Intent intent = new Intent(context, InfoActivity.class);
        intent.putExtra("target",target);
        intent.putExtra("role",role);
        startActivity(intent);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void showView() {
        setCanBack();
    }
}
