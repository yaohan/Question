package com.ssdut411.app.questionanswer.activity.parent;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.student.AnalysisActivity;
import com.ssdut411.app.questionanswer.activity.system.DrawerActivity;
import com.ssdut411.app.questionanswer.application.MainApplication;
import com.ssdut411.app.questionanswer.utils.T;

/**
 * Created by yao_han on 2015/11/28.
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
        return R.layout.activity_main_parent;
    }

    @Override
    protected void initViews() {
        super.initViews();
        getLinearLayout(R.id.ll_parent_analysis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChildAnalysisActivity();
            }
        });
        getLinearLayout(R.id.ll_parent_child_homework).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChildHomeworkActivity();
            }
        });;
        getLinearLayout(R.id.ll_parent_child_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChildTestActivity();
            }
        });;
    }


    @Override
    protected void loadData() {

    }

    @Override
    protected void showView() {
        super.showView();
    }

    private void startChildAnalysisActivity() {
                Intent intent = new Intent(context, AnalysisActivity.class);
                intent.putExtra("id", MainApplication.getInstance().getChildId());
                startActivity(intent);
    }

    private void startChildHomeworkActivity() {
        Intent intent = new Intent(MainPageActivity.this, ChildHomeworkActivity.class);
        startActivity(intent);
    }

    private void startChildTestActivity() {
        Intent intent = new Intent(MainPageActivity.this, ChildTestHistoryActivity.class);
        startActivity(intent);
    }
}
