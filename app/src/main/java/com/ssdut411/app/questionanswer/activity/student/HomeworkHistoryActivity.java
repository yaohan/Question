package com.ssdut411.app.questionanswer.activity.student;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.question.QuestionActivity;
import com.ssdut411.app.questionanswer.activity.question.ReportCardActivity;
import com.ssdut411.app.questionanswer.activity.system.BaseListViewActivity;
import com.ssdut411.app.questionanswer.activity.system.CommonAdapter;
import com.ssdut411.app.questionanswer.activity.system.ViewHolder;
import com.ssdut411.app.questionanswer.application.MainApplication;
import com.ssdut411.app.questionanswer.core.ActionCallbackListener;
import com.ssdut411.app.questionanswer.core.AppAction;
import com.ssdut411.app.questionanswer.core.AppActionImpl;
import com.ssdut411.app.questionanswer.model.Req.GetStudentHomeworkByIdReq;
import com.ssdut411.app.questionanswer.model.Req.GetStudentHomeworkReq;
import com.ssdut411.app.questionanswer.model.Resp.GetStudentHomeworkByIdResp;
import com.ssdut411.app.questionanswer.model.Resp.GetStudentHomeworkResp;
import com.ssdut411.app.questionanswer.model.model.ModelConfig;
import com.ssdut411.app.questionanswer.model.model.TestHistory;
import com.ssdut411.app.questionanswer.utils.GsonUtils;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.utils.T;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by yao_han on 2016/4/17.
 */
public class HomeworkHistoryActivity extends BaseListViewActivity {
    private List<TestHistory> list,todayList;
    private CommonAdapter firstAdapter, secondAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        setList();
    }

    @Override
    protected int initMenu() {
        return R.menu.menu_new;
    }

    @Override
    protected void clickSecondList(AdapterView<?> parent, View view, int position, long id) {
        startQuestionActivity(list.get(position));
    }

    private void startQuestionActivity(final TestHistory testHistory) {
        AppAction action = new AppActionImpl(context);
        GetStudentHomeworkByIdReq getStudentHomeworkByIdReq = new GetStudentHomeworkByIdReq();
        getStudentHomeworkByIdReq.setId(testHistory.getId());
        action.getStudentHomeworkById(getStudentHomeworkByIdReq, new ActionCallbackListener<GetStudentHomeworkByIdResp>() {
            @Override
            public void onSuccess(GetStudentHomeworkByIdResp data) {
                if (data.getResult() == GetStudentHomeworkResp.RESULT_SUCCESS) {
                    Intent intent;
                    if (testHistory.getState() == ModelConfig.STATE_FINISH) {
                        intent = new Intent(context, ReportCardActivity.class);
                    } else {
                        intent = new Intent(context, QuestionActivity.class);
                    }
                    intent.putExtra("source", "homework");
                    intent.putExtra("data", data.getStudentHomeworkModel());
                    L.i("data:" + GsonUtils.gsonToJsonString(data.getStudentHomeworkModel()));
                    startActivity(intent);
                } else {
                    T.showShort(context, data.getDesc());
                }
            }

            @Override
            public void onFailure(String message) {
                T.showShort(context, message);
            }
        });
    }

    @Override
    protected void clickFirstList(AdapterView<?> parent, View view, int position, long id) {
        startQuestionActivity(todayList.get(position));
    }

    @Override
    protected void loadData() {

    }

    private void setList() {
        AppAction action = new AppActionImpl(context);
        GetStudentHomeworkReq getStudentHomeworkReq = new GetStudentHomeworkReq();
        getStudentHomeworkReq.setId(MainApplication.getInstance().getUserId());
        action.getStudentHomework(getStudentHomeworkReq, new ActionCallbackListener<GetStudentHomeworkResp>() {
            @Override
            public void onSuccess(GetStudentHomeworkResp data) {
                if (data.getResult() == GetStudentHomeworkResp.RESULT_SUCCESS) {
                    list = data.getHomeworkList();
                    setList(list);
                } else {
                    emptyLayoutFirst.showError();
                    emptyLayoutSecond.showError();
                }
            }

            @Override
            public void onFailure(String message) {
                emptyLayoutFirst.showError();
                emptyLayoutSecond.showError();
            }
        });
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
        return "作业历史";
    }

    public void setList(List<TestHistory> list) {
        Collections.reverse(list);
        Date date = getDayStart(new Date());
        L.i("today first date:" + date);
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
            public void convert(ViewHolder viewHolder, TestHistory teacherHomework, int position) {
                viewHolder.getTextView(R.id.item_left).setText(new SimpleDateFormat("MM.dd").format(teacherHomework.getNewTime()));
                viewHolder.getTextView(R.id.item_center).setText(teacherHomework.getName());
                if (teacherHomework.getState() == ModelConfig.STATE_FINISH) {
                    viewHolder.getTextView(R.id.item_right).setText(getString(R.string.empty));
                    viewHolder.getImageView(R.id.item_right_image).setVisibility(View.VISIBLE);
                } else {
                    viewHolder.getTextView(R.id.item_right).setText(getString(R.string.not_finished));
                    viewHolder.getImageView(R.id.item_right_image).setVisibility(View.GONE);
                }
            }
        };
        lvSecondList.setAdapter(adapter);
        CommonAdapter<TestHistory> adapter1 = new CommonAdapter<TestHistory>(context,todayList,R.layout.item_homework) {
            @Override
            public void convert(ViewHolder viewHolder, TestHistory teacherHomework, int position) {
                viewHolder.getTextView(R.id.item_left).setText(getTime(teacherHomework.getNewTime()));
                viewHolder.getTextView(R.id.item_center).setText(teacherHomework.getName());
                if (teacherHomework.getState() == ModelConfig.STATE_FINISH) {
                    viewHolder.getTextView(R.id.item_right).setText(getString(R.string.empty));
                    viewHolder.getImageView(R.id.item_right_image).setVisibility(View.VISIBLE);
                } else {
                    viewHolder.getTextView(R.id.item_right).setText(getString(R.string.not_finished));
                    viewHolder.getImageView(R.id.item_right_image).setVisibility(View.GONE);
                }
            }
        };
        lvFirstList.setAdapter(adapter1);
    }
}
