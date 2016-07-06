package com.ssdut411.app.questionanswer.activity.person;

import android.view.View;
import android.widget.AdapterView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.system.BaseActivity;
import com.ssdut411.app.questionanswer.activity.system.BaseListViewActivity;
import com.ssdut411.app.questionanswer.activity.system.CommonAdapter;
import com.ssdut411.app.questionanswer.activity.system.ViewHolder;
import com.ssdut411.app.questionanswer.core.ActionCallbackListener;
import com.ssdut411.app.questionanswer.core.AppAction;
import com.ssdut411.app.questionanswer.core.AppActionImpl;
import com.ssdut411.app.questionanswer.model.Req.GetClazzDetailReq;
import com.ssdut411.app.questionanswer.model.Resp.GetClazzDetailResp;
import com.ssdut411.app.questionanswer.model.model.UserModel;
import com.ssdut411.app.questionanswer.utils.T;
import com.ssdut411.app.questionanswer.widget.EmptyLayout;

import java.util.List;

/**
 * Created by yao_han on 2016/4/7.
 */
public class ClassDetailActivity extends BaseListViewActivity {
    private String schoolId;
    private String classId;

    @Override
    protected int initMenu() {
        return 0;
    }

    @Override
    protected void clickSecondList(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    protected void clickFirstList(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    protected void loadData() {
        schoolId = getIntent().getStringExtra("schoolId");
        classId = getIntent().getStringExtra("classId");
        AppAction action = new AppActionImpl(context);
        GetClazzDetailReq getClazzDetailReq = new GetClazzDetailReq();
        getClazzDetailReq.setSchoolId(schoolId);
        getClazzDetailReq.setClassId(classId);
        action.getClazzDetail(getClazzDetailReq, new ActionCallbackListener<GetClazzDetailResp>() {
            @Override
            public void onSuccess(GetClazzDetailResp data) {
                if(data.getResult() == GetClazzDetailResp.RESULT_SUCCESS){
                    setList(data);
                }else{
                    emptyLayoutFirst.showError();
                    getTextView(R.id.tv_list_first).setVisibility(View.GONE);
                    getTextView(R.id.tv_list_second).setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String message) {
                T.showShort(context,message);
            }
        });


    }

    @Override
    protected void showView() {
        getTextView(R.id.tv_list_first).setText("老师");
        getTextView(R.id.tv_list_second).setText("家长");
    }

    @Override
    protected String initSecondText() {
        return "学生";
    }

    @Override
    protected String initFirstText() {
        return "老师";
    }

    @Override
    protected String initTitle() {
        return "班级详情";
    }

    public void setList(GetClazzDetailResp data) {
        List<UserModel> teacher = data.getTeachers();
        if(teacher.size() == 0){
            emptyLayoutFirst.showEmpty();
        }else{
            CommonAdapter<UserModel> teacherAdapter = new CommonAdapter<UserModel>(context,teacher,R.layout.item_person) {
                @Override
                public void convert(ViewHolder viewHolder, UserModel userModel, int position) {
                    viewHolder.getTextView(R.id.tv_person_name).setText(userModel.getRealName());
                }
            };
            lvFirstList.setAdapter(teacherAdapter);
        }

        List<UserModel> student = data.getStudents();
        if(student.size() == 0){
            emptyLayoutSecond.showEmpty();
        }else{
            CommonAdapter<UserModel> studentAdapter = new CommonAdapter<UserModel>(context,student,R.layout.item_person) {
                @Override
                public void convert(ViewHolder viewHolder, UserModel userModel, int position) {
                    viewHolder.getTextView(R.id.tv_person_name).setText(userModel.getRealName());
                }
            };
            lvSecondList.setAdapter(studentAdapter);
        }
    }
}
