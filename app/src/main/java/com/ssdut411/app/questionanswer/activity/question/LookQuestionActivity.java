package com.ssdut411.app.questionanswer.activity.question;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ssdut411.app.questionanswer.R;
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
 * Created by yao_han on 2015/12/1.
 */
public class LookQuestionActivity extends BaseActivity {
    private MenuItem menuCard;
    private boolean isCollection = false;
    private QuestionModel question;
    private int type;
    @Override
    protected String initTitle() {
        return getString(R.string.question_analysical);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        L.i("menu start");
        menuCard = menu.add(0, 1, 1, getString(R.string.collection));
        menuCard.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        if(isCollection){
            menuCard.setIcon(R.mipmap.icon_question_collection);
        }else{
            menuCard.setIcon(R.mipmap.icon_question_uncollection);
        }
        menuCard.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (isCollection) {
                    setUnCollection();
                } else {
                    setCollection();
                }
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
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
        type = getIntent().getIntExtra("state",-1);
    }

    @Override
    protected void showView() {

        setCanBack();
        question = null;
        question = (QuestionModel) getIntent().getSerializableExtra("question");
        L.i("question:"+question.toString());
        L.i("question collect:"+question.getCollection());
        isCollection = question.getCollection();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        QuestionFragment fragment = QuestionFragment.newInstance(question, type);
        fragmentTransaction.add(R.id.fl_look_layout, fragment);
        fragmentTransaction.commit();
    }

    private void setUnCollection() {
        isCollection = false;
        menuCard.setIcon(R.mipmap.icon_question_uncollection);
        AppAction appAction = new AppActionImpl(context);
        UnCollectionReq unCollectionReq = new UnCollectionReq();
        unCollectionReq.setPsId(MainApplication.getInstance().getUserId());
        unCollectionReq.setqId(question.getqId());
        unCollectionReq.setCollectionId("");
        appAction.unCollection(unCollectionReq, new ActionCallbackListener<UnCollectionResp>() {
            @Override
            public void onSuccess(UnCollectionResp data) {
                T.showShort(context, data.getDesc());
            }

            @Override
            public void onFailure(String message) {
                T.showShort(context, getString(R.string.error_message));
            }
        });
    }

    private void setCollection() {
        isCollection = true;
        menuCard.setIcon(R.mipmap.icon_question_collection);
        AppAction appAction = new AppActionImpl(context);
        CollectionReq collectionReq = new CollectionReq();
        collectionReq.setPsId(MainApplication.getInstance().getUserId());
        collectionReq.setqId(question.getqId());
        appAction.collection(collectionReq, new ActionCallbackListener<CollectionResp>() {
            @Override
            public void onSuccess(CollectionResp data) {
                T.showShort(context, data.getDesc());
            }

            @Override
            public void onFailure(String message) {
                T.showShort(context, getString(R.string.error_message));
            }
        });
    }
}
