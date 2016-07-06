package com.ssdut411.app.questionanswer.activity.teacher;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.question.CheckQuestionActivity;
import com.ssdut411.app.questionanswer.activity.system.BaseListViewActivity;
import com.ssdut411.app.questionanswer.activity.system.CommonAdapter;
import com.ssdut411.app.questionanswer.activity.system.ViewHolder;
import com.ssdut411.app.questionanswer.application.MainApplication;
import com.ssdut411.app.questionanswer.core.ActionCallbackListener;
import com.ssdut411.app.questionanswer.core.AppAction;
import com.ssdut411.app.questionanswer.core.AppActionImpl;
import com.ssdut411.app.questionanswer.model.Req.GetHomeworkByIdReq;
import com.ssdut411.app.questionanswer.model.Req.GetTeacherHomeworkReq;
import com.ssdut411.app.questionanswer.model.Resp.GetHomeworkByIdResp;
import com.ssdut411.app.questionanswer.model.Resp.GetTeacherHomeworkResp;
import com.ssdut411.app.questionanswer.model.model.ModelConfig;
import com.ssdut411.app.questionanswer.utils.GsonUtils;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.utils.T;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by yao_han on 2016/4/13.
 */
public class HomeworkHistoryActivity extends BaseListViewActivity {

    private List<GetTeacherHomeworkResp.TeacherHomework> list,todayList;

    @Override
    protected int initMenu() {
        return 0;
    }

    @Override
    protected void initViews() {
        super.initViews();
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.actions_new:
                        startPostHomeworkActivity();
                        break;
                }
                return true;
            }
        });
    }

    private void startPostHomeworkActivity() {
        startActivity(new Intent(context, PostHomeworkActivity.class));
    }

    @Override
    protected void clickSecondList(AdapterView<?> parent, View view, int position, long id) {
        startQuestionDetailActivity(list.get(position));
    }

    private void startQuestionDetailActivity(GetTeacherHomeworkResp.TeacherHomework teacherHomework) {
        if(teacherHomework.getFinishes() == 0){
            T.showShort(context,"还没有人提交作业");
            return;
        }
        AppAction action = new AppActionImpl(context);
        GetHomeworkByIdReq getHomeworkByIdReq = new GetHomeworkByIdReq();
        getHomeworkByIdReq.setId(teacherHomework.getId());
        action.getHomeworkById(getHomeworkByIdReq, new ActionCallbackListener<GetHomeworkByIdResp>() {
            @Override
            public void onSuccess(GetHomeworkByIdResp data) {
                if (data.getResult() == GetHomeworkByIdResp.RESULT_SUCCESS) {
                    if (data.getTeacherHomeworkModel() != null) {
                        Intent intent = new Intent(context, HomeworkDetailActivity.class);
                        intent.putExtra("data", GsonUtils.gsonToJsonString(data.getTeacherHomeworkModel().getStudentHomeworkModels()));
                        startActivity(intent);
                    } else {
                        T.showShort(context, "model is null");
                    }
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
        startQuestionDetailActivity(todayList.get(position));
    }

    @Override
    protected void loadData() {
        AppAction action = new AppActionImpl(context);
        GetTeacherHomeworkReq getTeacherHomeworkReq = new GetTeacherHomeworkReq();
        getTeacherHomeworkReq.setId(MainApplication.getInstance().getUserId());
        action.getTeacherHomework(getTeacherHomeworkReq, new ActionCallbackListener<GetTeacherHomeworkResp>() {
            @Override
            public void onSuccess(GetTeacherHomeworkResp data) {
                if (data.getResult() == GetTeacherHomeworkResp.RESULT_SUCCESS) {
                    list = data.getList();
                    if (list == null) {
                        emptyLayoutFirst.showError();
                        emptyLayoutSecond.showError();
                    } else if (list.size() == 0) {
                        emptyLayoutFirst.showEmpty();
                        emptyLayoutSecond.showEmpty();
                    } else {
                        setList();
                    }
                }
            }

            @Override
            public void onFailure(String message) {

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
        return "批阅作业";
    }

    private void setList() {
        List<GetTeacherHomeworkResp.TeacherHomework> temp = new ArrayList<>();
        temp.addAll(list);
        L.i("temp:"+temp.size());
        list.removeAll(list);
        L.i("temp:"+temp.size());
        for(int i=0;i<temp.size();i++){
            if(temp.get(i).getState() != ModelConfig.STATE_NEW){
                list.add(temp.get(i));
            }
        }
        Collections.reverse(list);
        Date date = getDayStart(new Date());
        L.i("today first date:" + date);
        int i;
        for(i=0;i<list.size();i++){
            L.i("time date:"+list.get(i).getNewDate());
            if((list.get(i).getNewDate().compareTo(date))<0){
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

        CommonAdapter<GetTeacherHomeworkResp.TeacherHomework> adapter = new CommonAdapter<GetTeacherHomeworkResp.TeacherHomework>(this, list, R.layout.item_homework) {
            @Override
            public void convert(ViewHolder viewHolder, GetTeacherHomeworkResp.TeacherHomework teacherHomework, int position) {
                viewHolder.getTextView(R.id.item_left).setText(new SimpleDateFormat("MM.dd").format(teacherHomework.getNewDate()));
                viewHolder.getTextView(R.id.item_center).setText(teacherHomework.getName());
                viewHolder.getTextView(R.id.item_right).setText(teacherHomework.getFinishes()+"/"+teacherHomework.getCounts());
                viewHolder.getImageView(R.id.item_right_image).setVisibility(View.GONE);
            }
        };
        lvSecondList.setAdapter(adapter);
        CommonAdapter<GetTeacherHomeworkResp.TeacherHomework> adapter1 = new CommonAdapter<GetTeacherHomeworkResp.TeacherHomework>(context,todayList,R.layout.item_homework) {
            @Override
            public void convert(ViewHolder viewHolder, GetTeacherHomeworkResp.TeacherHomework teacherHomework, int position) {
                viewHolder.getTextView(R.id.item_left).setText(getTime(teacherHomework.getNewDate()));
                viewHolder.getTextView(R.id.item_center).setText(teacherHomework.getName());
                viewHolder.getTextView(R.id.item_right).setText(teacherHomework.getFinishes()+"/"+teacherHomework.getCounts());
                viewHolder.getImageView(R.id.item_right_image).setVisibility(View.GONE);
            }
        };
        lvFirstList.setAdapter(adapter1);
    }

    protected String getTime(Date date){
        DecimalFormat df = new DecimalFormat("00");
        L.i("minute:"+date.getMinutes());
        L.i("format:"+df.format(date.getMinutes()));
        return df.format(date.getHours())+":"+df.format(date.getMinutes());
    }
    public Date getDayStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
        return calendar.getTime();
    }
}
