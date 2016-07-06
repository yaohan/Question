package com.ssdut411.app.questionanswer.activity.teacher;

import android.content.Intent;
import android.view.View;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.system.BaseActivity;
import com.ssdut411.app.questionanswer.activity.system.DrawerActivity;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.utils.T;



/**
 * Created by yao_han on 2015/11/29.
 */
public class MainPageActivity extends DrawerActivity{

    @Override
    protected String initTitle() {
        return getString(R.string.app_name);
    }

    @Override
    protected int initMenu() {
        return R.menu.menu_null;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_main_teacher;
    }

    @Override
    protected void initViews() {
        super.initViews();
        getLinearLayout(R.id.ll_teacher_analysis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnalysisActivity();
            }
        });
        getLinearLayout(R.id.ll_teacher_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPostHomeworkActivity();
            }
        });
        getLinearLayout(R.id.ll_teacher_check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCheckHomeworkActivity();
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void showView() {
        super.showView();
        setTitle(getString(R.string.app_name));
    }

    private void startAnalysisActivity() {
        startActivity(new Intent(context,TeacherAnalysisActivity.class));
    }

    private void startPostHomeworkActivity() {
        Intent intent = new Intent(MainPageActivity.this, PostHomeworkActivity.class);
        startActivity(intent);
    }

    private void startCheckHomeworkActivity() {
        Intent intent = new Intent(MainPageActivity.this, HomeworkHistoryActivity.class);
        startActivity(intent);
    }
}

