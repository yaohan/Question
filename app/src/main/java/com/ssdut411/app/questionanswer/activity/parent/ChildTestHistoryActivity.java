package com.ssdut411.app.questionanswer.activity.parent;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.student.NewTestActivity;
import com.ssdut411.app.questionanswer.activity.question.ReportCardActivity;
import com.ssdut411.app.questionanswer.activity.system.BaseListViewActivity;
import com.ssdut411.app.questionanswer.activity.system.CommonAdapter;
import com.ssdut411.app.questionanswer.activity.system.ViewHolder;
import com.ssdut411.app.questionanswer.application.MainApplication;
import com.ssdut411.app.questionanswer.core.ActionCallbackListener;
import com.ssdut411.app.questionanswer.core.AppAction;
import com.ssdut411.app.questionanswer.core.AppActionImpl;
import com.ssdut411.app.questionanswer.model.Req.GetMyTestReq;
import com.ssdut411.app.questionanswer.model.Req.GetSelfTestByIdReq;
import com.ssdut411.app.questionanswer.model.Resp.GetMyTestResp;
import com.ssdut411.app.questionanswer.model.Resp.GetSelfTestByIdResp;
import com.ssdut411.app.questionanswer.model.model.ModelConfig;
import com.ssdut411.app.questionanswer.model.model.SelfTestModel;
import com.ssdut411.app.questionanswer.model.model.TestHistory;
import com.ssdut411.app.questionanswer.utils.GsonUtils;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.utils.SPUtils;
import com.ssdut411.app.questionanswer.utils.T;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by yao_han on 2016/4/20.
 */
public class ChildTestHistoryActivity extends BaseListViewActivity {
    private List<TestHistory> list,todayList;

    @Override
    protected void onResume() {
        super.onResume();
        emptyLayoutFirst.showLoading();
        emptyLayoutSecond.showLoading();
        getMyList();
    }

    @Override
    protected int initMenu() {
        return 0;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_listview;
    }

    @Override
    protected void initViews() {
        super.initViews();
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.actions_new:
                        startMyTestActivity();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void clickSecondList(AdapterView<?> parent, View view, int position, long id) {
        startQuestionActivity(list.get(position));
    }

    @Override
    protected void clickFirstList(AdapterView<?> parent, View view, int position, long id) {
        startQuestionActivity(todayList.get(position));
    }



    @Override
    protected void loadData() {

    }
    private void startMyTestActivity() {
        Intent intent = new Intent(context,NewTestActivity.class);
        startActivity(intent);
    }

    public void getMyList() {
        AppAction action = new AppActionImpl(this);
        GetMyTestReq getMyTestReq = new GetMyTestReq();
        getMyTestReq.setPsId(MainApplication.getInstance().getChildId());
        action.getMyTest(getMyTestReq, new ActionCallbackListener<GetMyTestResp>() {
            @Override
            public void onSuccess(GetMyTestResp data) {
                if (data.getResult() == GetMyTestResp.RESULT_SUCCESS) {
                    list = data.getTestInfoList();
                    if (list == null) {
                        emptyLayoutFirst.showError();
                        emptyLayoutSecond.showError();
                    } else if (list.size() == 0) {
                        emptyLayoutFirst.showEmpty();
                        emptyLayoutSecond.showEmpty();
                    } else {
                        L.i("get history from server");
                        setList();
                        SPUtils.put(context, "SelfTestHistory", GsonUtils.gsonToJsonString(list));
                    }
                } else {
                    T.showShort(context, data.getDesc());
                }
            }

            @Override
            public void onFailure(String message) {
                T.showShort(context, getString(R.string.error_message));
                emptyLayoutFirst.showError();
                emptyLayoutSecond.showError();
                L.i("message = " + message);
            }
        });
    }

    private void setList() {
        Collections.reverse(list);
        Date date = getDayStart(new Date());
        L.i("today first date:"+date);
        int i;
        for(i=0;i<list.size();i++){
            L.i("time date:"+list.get(i).getNewTime());
            if((list.get(i).getNewTime().compareTo(date))<0){
                L.i("before");
                break;
            }else{
                L.i("today");
            }
        }
        L.i("i:"+i);
        L.i("size:"+list.size());
        todayList = list.subList(0,i);
        list = list.subList(i,list.size());
        L.i("today size:"+todayList.size()+"  list size:"+list.size());
        if(todayList.size() == 0){
            emptyLayoutFirst.showEmpty();
        }
        if(list.size() == 0){
            emptyLayoutSecond.showEmpty();
        }

        CommonAdapter<TestHistory> adapter = new CommonAdapter<TestHistory>(this, list, R.layout.item_homework) {
            @Override
            public void convert(ViewHolder viewHolder, TestHistory testInfo, int position) {
                viewHolder.getTextView(R.id.item_left).setText(getTime(testInfo.getNewTime()));
                viewHolder.getTextView(R.id.item_center).setText(testInfo.getName());
                if (testInfo.getState() == ModelConfig.STATE_FINISH) {
                    viewHolder.getTextView(R.id.item_right).setText(getString(R.string.empty));
                    viewHolder.getImageView(R.id.item_right_image).setVisibility(View.VISIBLE);
                } else {
                    viewHolder.getTextView(R.id.item_right).setText(getString(R.string.state_unfinished));
                    viewHolder.getImageView(R.id.item_right_image).setVisibility(View.GONE);
                }
            }
        };
        lvSecondList.setAdapter(adapter);
        CommonAdapter<TestHistory> adapter1 = new CommonAdapter<TestHistory>(context,todayList,R.layout.item_homework) {
            @Override
            public void convert(ViewHolder viewHolder, TestHistory testHistory, int position) {


                viewHolder.getTextView(R.id.item_left).setText(getTime(testHistory.getNewTime()));
                viewHolder.getTextView(R.id.item_center).setText(testHistory.getName());
                if (testHistory.getState() == ModelConfig.STATE_FINISH) {
                    viewHolder.getTextView(R.id.item_right).setText(getString(R.string.empty));
                    viewHolder.getImageView(R.id.item_right_image).setVisibility(View.VISIBLE);
                } else {
                    viewHolder.getTextView(R.id.item_right).setText(getString(R.string.state_unfinished));
                    viewHolder.getImageView(R.id.item_right_image).setVisibility(View.GONE);
                }
            }
        };
        lvFirstList.setAdapter(adapter1);
    }

    @Override
    protected void showView() {
        String title = getIntent().getStringExtra("title");
        L.i("title = " + title);
        if (title == null) {
            setTitle(getString(R.string.test_history));
        } else {
            setTitle(title);
        }
        setCanBack();
    }

    @Override
    protected String initSecondText() {
        return "以前";
    }

    @Override
    protected String initFirstText() {
        return "今天";
    }

    @Override
    protected String initTitle() {
        return "孩子自测";
    }

    private void startReportCardOrQuestionActivity(SelfTestModel selfTestModel, int state) {
        if (state == ModelConfig.STATE_NOT_FINISH) {
//            intent = new Intent(context, QuestionActivity.class);
            T.showShort(context,"该作业还没完成");
        } else {
            Intent intent = new Intent(context, ReportCardActivity.class);
            intent.putExtra("source","test");
            intent.putExtra("data", selfTestModel);
            startActivity(intent);
        }
    }

    private void startQuestionActivity(TestHistory testHistory) {
        final String id = testHistory.getId();
        final int state = testHistory.getState();
        String strModel = "";
        if (!strModel.equals("")) {
            L.i("get SelfModel from sp");
            startReportCardOrQuestionActivity(GsonUtils.gsonToObject(strModel, SelfTestModel.class), state);
        } else {
            L.i("get SelfModel from server");
            AppAction action = new AppActionImpl(this);
            GetSelfTestByIdReq getSelfTestByIdReq = new GetSelfTestByIdReq();
            getSelfTestByIdReq.setId(id);
            action.getSelfTestById(getSelfTestByIdReq, new ActionCallbackListener<GetSelfTestByIdResp>() {
                @Override
                public void onSuccess(GetSelfTestByIdResp data) {
                    if (data.getResult() == GetSelfTestByIdResp.RESULT_SUCCESS) {
                        startReportCardOrQuestionActivity(data.getSelfTestModel(), state);
                        SPUtils.put(context, id, GsonUtils.gsonToJsonString(data.getSelfTestModel()));
                    } else {
                        T.showShort(context, data.getDesc());
                    }
                }

                @Override
                public void onFailure(String message) {
                    T.showShort(context, getString(R.string.error_message));
                }
            });
        }
    }


}