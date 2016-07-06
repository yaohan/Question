package com.ssdut411.app.questionanswer.activity.teacher;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.question.LookQuestionActivity;
import com.ssdut411.app.questionanswer.activity.question.QuestionFragment;
import com.ssdut411.app.questionanswer.activity.system.BaseActivity;
import com.ssdut411.app.questionanswer.application.MainApplication;
import com.ssdut411.app.questionanswer.core.ActionCallbackListener;
import com.ssdut411.app.questionanswer.core.AppAction;
import com.ssdut411.app.questionanswer.core.AppActionImpl;
import com.ssdut411.app.questionanswer.model.Req.CollectionReq;
import com.ssdut411.app.questionanswer.model.Req.UnCollectionReq;
import com.ssdut411.app.questionanswer.model.Resp.CollectionResp;
import com.ssdut411.app.questionanswer.model.Resp.UnCollectionResp;
import com.ssdut411.app.questionanswer.model.model.QuestionModel;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.utils.T;

/**
 * Created by yao_han on 2016/5/29.
 */
public class QuestionDetailActivity extends BaseActivity {
    private QuestionModel question;

    @Override
    protected String initTitle() {
        return getString(R.string.question_analysical);
    }

    @Override
    protected int initMenu() {
        return 0;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_look_question;
    }

    @Override
    protected void initViews() {
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        question = null;
        question = (QuestionModel) getIntent().getSerializableExtra("question");
        String detail = getIntent().getStringExtra("detail");
        L.i("question:"+question.toString());
        L.i("question collect:"+question.getCollection());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        QuestionFragment fragment = QuestionFragment.newInstance(question, detail, QuestionFragment.STATE_TEACHER);
        fragmentTransaction.add(R.id.fl_look_layout, fragment);
        fragmentTransaction.commit();
    }
}