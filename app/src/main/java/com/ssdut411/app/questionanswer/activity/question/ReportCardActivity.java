package com.ssdut411.app.questionanswer.activity.question;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;


import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.system.BaseActivity;
import com.ssdut411.app.questionanswer.application.MainApplication;
import com.ssdut411.app.questionanswer.core.ActionCallbackListener;
import com.ssdut411.app.questionanswer.core.AppAction;
import com.ssdut411.app.questionanswer.core.AppActionImpl;
import com.ssdut411.app.questionanswer.model.Req.SubmitStudentHomeworkReq;
import com.ssdut411.app.questionanswer.model.Resp.SubmitStudentHomeworkResp;
import com.ssdut411.app.questionanswer.model.Resp.SubmitStudentResp;
import com.ssdut411.app.questionanswer.model.model.CommentInfo;
import com.ssdut411.app.questionanswer.model.model.SelfTestModel;
import com.ssdut411.app.questionanswer.model.model.StudentHomeworkModel;
import com.ssdut411.app.questionanswer.utils.GsonUtils;
import com.ssdut411.app.questionanswer.utils.KeyBoardUtils;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.utils.T;
import com.ssdut411.app.questionanswer.widget.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yao_han on 2015/12/1.
 */
public class ReportCardActivity extends BaseActivity {

    private SelfTestModel selfTestModel;
    private ViewPager vpReport;
    private List<Fragment> fragmentList;
    private String source;
    private ViewPagerIndicator mIndicator;
    private boolean cmd = false;
    private EvaluationFragment evaluationFragment;
    private List<CommentInfo> commentInfoList;
    private StudentHomeworkModel studentHomeworkModel;

    @Override
    protected String initTitle() {
        return getString(R.string.report_card);
    }

    @Override
    protected int initMenu() {
       if(cmd){
           return R.menu.menu_commond;
       }
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
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.actions_commond:
                        getLinearLayout(R.id.commond).setVisibility(View.VISIBLE);
                        getEditText(R.id.et_commond).requestFocus();
                        KeyBoardUtils.openKeyboard(getEditText(R.id.et_commond), context);
                        break;
                }
                return false;
            }
        });
        getTextView(R.id.bt_commond).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getEditText(R.id.et_commond).getText().length() == 0){
                    T.showShort(context,"评论内容不能为空");
                }else{
                    CommentInfo commentInfo = new CommentInfo();
                    commentInfo.setComment(getEditText(R.id.et_commond).getText().toString());
                    commentInfo.setName(MainApplication.getInstance().getUser().getRealName());
                    commentInfo.setRole(MainApplication.getInstance().getRole());
                    commentInfo.setHeadPortrait("");
                    commentInfoList.add(commentInfo);
                    L.i("update:"+ GsonUtils.gsonToJsonString(commentInfoList));
                    evaluationFragment.updateEvaluation(commentInfoList);

                    studentHomeworkModel.setCommentList(commentInfoList);
                    AppAction action = new AppActionImpl(context);
                    SubmitStudentHomeworkReq submitStudentHomeworkReq = new SubmitStudentHomeworkReq();
                    submitStudentHomeworkReq.setStudentHomeworkModel(studentHomeworkModel);
                    L.i("model:"+GsonUtils.gsonToJsonString(studentHomeworkModel));
                    action.submitStudentHomework(submitStudentHomeworkReq, new ActionCallbackListener<SubmitStudentHomeworkResp>() {
                        @Override
                        public void onSuccess(SubmitStudentHomeworkResp data) {
                            if(data.getResult() == SubmitStudentResp.RESULT_SUCCESS){
                                getLinearLayout(R.id.commond).setVisibility(View.GONE);
                                getEditText(R.id.et_commond).setText("");
                                KeyBoardUtils.closeKeyboard(getEditText(R.id.et_commond), context);
                                T.showShort(context,"评论成功");
                            }else{
                                T.showShort(context, data.getDesc());
                            }

                        }

                        @Override
                        public void onFailure(String message) {
                            T.showShort(context,getString(R.string.error_message));
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void loadData() {
        selfTestModel = (SelfTestModel) getIntent().getSerializableExtra("data");
    }

    @Override
    protected void showView() {
        source = getIntent().getStringExtra("source");
        setCanBack();
        if(source.equals("homework")){
            cmd = true;
            studentHomeworkModel = (StudentHomeworkModel)selfTestModel;
            commentInfoList = studentHomeworkModel.getCommentList();
            if(commentInfoList == null){
                commentInfoList = new ArrayList<>();
            }
        }
        initDatas();
    }

    private void initDatas() {
        if(studentHomeworkModel == null){
            evaluationFragment = EvaluationFragment.newInstance(selfTestModel, source);
        }else{
            evaluationFragment = EvaluationFragment.newInstance1(studentHomeworkModel, source);
        }
        TopicDetailFragment topicDetailFragment = TopicDetailFragment.newInstance(selfTestModel);
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(evaluationFragment);
        fragmentList.add(topicDetailFragment);

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
        List<String> mDatas = Arrays.asList(getString(R.string.evaluation_situation),getString(R.string.topic_detail));
//        mIndicator.setTabItemTitles(mDatas);
        vpReport.setAdapter(mAdapter);
        mIndicator.setViewPager(vpReport,0);
    }
}
