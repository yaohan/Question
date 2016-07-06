package com.ssdut411.app.questionanswer.activity.student;

import android.content.Intent;
import android.view.View;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.person.LoginActivity;
import com.ssdut411.app.questionanswer.activity.system.DrawerActivity;
import com.ssdut411.app.questionanswer.utils.T;
import com.ssdut411.app.questionanswer.widget.dialog.BaseDialog;
import com.ssdut411.app.questionanswer.widget.dialog.SimpleDialog;


/**
 * Created by yao_han on 2015/11/29.
 */
public class MainPageActivity extends DrawerActivity {

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
        getLinearLayout(R.id.ll_main_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTestHistoryActivity();
            }
        });
        getLinearLayout(R.id.ll_main_homework).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHomeworkActivity();
            }
        });
        getLinearLayout(R.id.ll_main_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startErrorQuestionActivity();
            }
        });
        getLinearLayout(R.id.ll_main_collection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCollectionQuestionActivity();
            }
        });
        getLinearLayout(R.id.ll_main_analysis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnalysisActivity();
            }
        });
    }

    private void startHomeworkActivity() {
        startActivity(new Intent(context,HomeworkHistoryActivity.class));
    }

    @Override
    protected void loadData() {

    }


    @Override
    protected void showView() {
        super.showView();
        setBackTips();
        setTitle(getString(R.string.app_name));
        loginDialog = initLoginDialog();
    }

    private void startAnalysisActivity(){
        startActivity(new Intent(context, AnalysisActivity.class));
    }
    private void startErrorQuestionActivity() {
        startActivity(new Intent(context, ErrorQuestionActivity.class));
    }

    private void startCollectionQuestionActivity() {
        startActivity(new Intent(context, CollectQuestionActivity.class));
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
