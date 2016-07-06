package com.ssdut411.app.questionanswer.activity.teacher;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.question.EvaluationFragment;
import com.ssdut411.app.questionanswer.activity.question.TopicDetailFragment;
import com.ssdut411.app.questionanswer.activity.system.BaseActivity;
import com.ssdut411.app.questionanswer.model.model.StudentHomeworkModel;
import com.ssdut411.app.questionanswer.utils.GsonUtils;
import com.ssdut411.app.questionanswer.widget.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yao_han on 2016/5/29.
 */
public class HomeworkDetailActivity extends BaseActivity {
    private List<StudentHomeworkModel> studentHomeworkModelList;
    private ViewPager vpReport;
    private List<Fragment> fragmentList;
    private String source;
    private ViewPagerIndicator mIndicator;
    private int average;

    @Override
    protected String initTitle() {
        return "作业详情";
    }

    @Override
    protected int initMenu() {
        return 0;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_report_card;
    }

    @Override
    protected void initViews() {
        vpReport = getViewPager(R.id.vp_report_card_viewpager);
        mIndicator = (ViewPagerIndicator)findViewById(R.id.id_indicator);
    }

    @Override
    protected void loadData() {
        String data = getIntent().getStringExtra("data");
        studentHomeworkModelList = GsonUtils.gsonToList(data,StudentHomeworkModel.class);
    }

    @Override
    protected void showView() {
        setCanBack();

        initDatas();
    }
    private void initDatas() {
        ClassListFragment classListFragment = ClassListFragment.newInstance(studentHomeworkModelList);
        QuestionListFragment questionListFragment = QuestionListFragment.newInstance(studentHomeworkModelList);
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(classListFragment);
        fragmentList.add(questionListFragment);

        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }
        };
        List<String> mDatas = Arrays.asList(getString(R.string.evaluation_situation), getString(R.string.topic_detail));
//        mIndicator.setTabItemTitles(mDatas);
        vpReport.setAdapter(mAdapter);
        mIndicator.setViewPager(vpReport, 0);
    }

    public double getAverage() {
        double sum = 0;
        int count = 0;
        for(StudentHomeworkModel studentHomeworkModel:studentHomeworkModelList){
            sum+=studentHomeworkModel.getGrade();
            count++;
        }
        return sum/count;
    }
}
