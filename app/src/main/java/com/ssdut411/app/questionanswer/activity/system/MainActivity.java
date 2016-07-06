package com.ssdut411.app.questionanswer.activity.system;

import android.content.Intent;
import android.view.View;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.person.LoginActivity;
import com.ssdut411.app.questionanswer.activity.student.CollectQuestionActivity;
import com.ssdut411.app.questionanswer.activity.student.ErrorQuestionActivity;
import com.ssdut411.app.questionanswer.activity.student.TestHistoryActivity;
import com.ssdut411.app.questionanswer.utils.T;
import com.ssdut411.app.questionanswer.widget.dialog.BaseDialog;
import com.ssdut411.app.questionanswer.widget.dialog.SimpleDialog;

/**
 * 未登录用户的主页
 *
 * Created by LENOVO on 2016/1/10.
 */
public class MainActivity extends DrawerActivity{

    private BaseDialog loginDialog;

    @Override
    protected String initTitle() {
        return getString(R.string.app_name);
    }

    @Override
    protected int initMenu() {
        return 0;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        super.initViews();
        //我的自测
        getLinearLayout(R.id.ll_main_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTestHistoryActivity();
            }
        });
        //我的作业
        getLinearLayout(R.id.ll_main_homework).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDialog.show();
            }
        });
        //我的错题
        getLinearLayout(R.id.ll_main_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startErrorQuestionActivity();
            }
        });
        //我的收藏
        getLinearLayout(R.id.ll_main_collection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCollectionQuestionActivity();
            }
        });
        //我的分析
        getLinearLayout(R.id.ll_main_analysis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDialog.show();
            }
        });
    }

    @Override
    protected void loadData() {

    }


    @Override
    protected void showView() {
        super.showView();
        //设置实体返回键提示
        setBackTips();

        //设置info信息
        getLinearLayout(R.id.ll_menu_info).setVisibility(View.GONE);
        getTextView(R.id.tv_menu_register_login).setVisibility(View.VISIBLE);
        //设置标题
        setTitle(getString(R.string.app_name));

        //初始化登录dialog
        loginDialog = initLoginDialog();
    }
    
    private void startErrorQuestionActivity() {
        Intent intent = new Intent(context, ErrorQuestionActivity.class);
        intent.putExtra(getString(R.string.string_target),getString(R.string.my_error));
        startActivity(intent);
    }

    private void startCollectionQuestionActivity() {
        Intent intent = new Intent(context, CollectQuestionActivity.class);
        intent.putExtra(getString(R.string.string_target),getString(R.string.my_collection));
        startActivity(intent);
    }

    private void startTestHistoryActivity() {
        Intent intent = new Intent(context, TestHistoryActivity.class);
        startActivity(intent);
    }

    private void startLoginActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
    }

    private BaseDialog initLoginDialog() {
        return new SimpleDialog.Builder(context,R.layout.dialog_easy).setTitle(getString(R.string.please_login))
                .setDesc(getString(R.string.login_for_more_function))
                .setPositiveButton(getString(R.string.sure), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loginDialog.dismiss();
                        startLoginActivity();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loginDialog.dismiss();
                    }
                }).getDialog();
    }
}
