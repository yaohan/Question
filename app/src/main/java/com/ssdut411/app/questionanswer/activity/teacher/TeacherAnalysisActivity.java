package com.ssdut411.app.questionanswer.activity.teacher;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.student.AnalysisActivity;
import com.ssdut411.app.questionanswer.activity.system.BaseActivity;
import com.ssdut411.app.questionanswer.activity.system.CommonAdapter;
import com.ssdut411.app.questionanswer.activity.system.ViewHolder;
import com.ssdut411.app.questionanswer.application.MainApplication;
import com.ssdut411.app.questionanswer.core.ActionCallbackListener;
import com.ssdut411.app.questionanswer.core.AppAction;
import com.ssdut411.app.questionanswer.core.AppActionImpl;
import com.ssdut411.app.questionanswer.model.Req.GetClazzDetailReq;
import com.ssdut411.app.questionanswer.model.Resp.GetClazzDetailResp;
import com.ssdut411.app.questionanswer.model.model.UserModel;

import java.util.List;

/**
 * Created by yao_han on 2016/5/29.
 */
public class TeacherAnalysisActivity extends BaseActivity {
    ListView listView;
    List<UserModel> list;
    @Override
    protected String initTitle() {
        return "成绩分析";
    }

    @Override
    protected int initMenu() {
        return 0;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_analysis;
    }

    @Override
    protected void initViews() {
        listView = getListView(R.id.lv_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, AnalysisActivity.class);
                intent.putExtra("id",list.get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void loadData() {
        AppAction action = new AppActionImpl(context);
        GetClazzDetailReq clazzDetailReq = new GetClazzDetailReq();
        clazzDetailReq.setSchoolId(MainApplication.getInstance().getUser().getSchoolId());
        clazzDetailReq.setClassId(MainApplication.getInstance().getUser().getClassId());
        action.getClazzDetail(clazzDetailReq, new ActionCallbackListener<GetClazzDetailResp>() {
            @Override
            public void onSuccess(GetClazzDetailResp data) {
                if(data.getResult() == GetClazzDetailResp.RESULT_SUCCESS){
                    list = data.getStudents();
                    CommonAdapter<UserModel> adapter = new CommonAdapter<UserModel>(context,list,R.layout.item_listview) {
                        @Override
                        public void convert(ViewHolder viewHolder, UserModel userModel, int position) {
                            viewHolder.getTextView(R.id.item_center).setText(userModel.getRealName());
                        }
                    };
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    @Override
    protected void showView() {
        setCanBack();
    }
}
