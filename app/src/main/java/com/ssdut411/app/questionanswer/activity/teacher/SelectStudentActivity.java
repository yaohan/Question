package com.ssdut411.app.questionanswer.activity.teacher;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.person.SearchPersonActivity;
import com.ssdut411.app.questionanswer.activity.system.BaseListViewActivity;
import com.ssdut411.app.questionanswer.activity.system.CommonAdapter;
import com.ssdut411.app.questionanswer.activity.system.ViewHolder;
import com.ssdut411.app.questionanswer.application.MainApplication;
import com.ssdut411.app.questionanswer.core.ActionCallbackListener;
import com.ssdut411.app.questionanswer.core.AppAction;
import com.ssdut411.app.questionanswer.core.AppActionImpl;
import com.ssdut411.app.questionanswer.model.Req.GetClazzDetailReq;
import com.ssdut411.app.questionanswer.model.Req.SubmitStudentReq;
import com.ssdut411.app.questionanswer.model.Resp.GetClazzDetailResp;
import com.ssdut411.app.questionanswer.model.Resp.LoginResp;
import com.ssdut411.app.questionanswer.model.Resp.SubmitStudentResp;
import com.ssdut411.app.questionanswer.model.model.ModelConfig;
import com.ssdut411.app.questionanswer.model.model.TeacherHomeworkModel;
import com.ssdut411.app.questionanswer.model.model.UserModel;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.utils.T;
import com.ssdut411.app.questionanswer.widget.dialog.BaseDialog;
import com.ssdut411.app.questionanswer.widget.dialog.PostDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yao_han on 2015/11/28.
 */
public class SelectStudentActivity extends BaseListViewActivity {
    private static final int REQUEST = 0;
    private List<String> group;
    private List<String> student,studentIdList;
    private CommonAdapter<String> groupAdapter,studentAdapter;
    private PostDialog postDialog;
    private TeacherHomeworkModel teacherHomeworkModel;
    private List<UserModel> list;

    @Override
    protected String initTitle() {
        return getString(R.string.select_student);
    }

    @Override
    protected int initMenu() {
        return R.menu.menu_search;
    }

    @Override
    protected void initViews() {
        super.initViews();
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.actions_search:
                        startSearchPersonActivity();
                        break;
                    case R.id.actions_sure:
                        postDialog.show();
                        postDialog.setStudent(getStudent(student));
                        break;
                }
                return true;
            }
        });
        getTextView(R.id.tv_select_all).setVisibility(View.VISIBLE);
        getTextView(R.id.tv_select_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(UserModel user:list){
                    if(!studentIdList.contains(user.getId())){
                        student.add(user.getRealName());
                        studentIdList.add(user.getId());
                    }
                }
                studentAdapter.notifyDataSetChanged();
            }
        });
    }



    private void startSearchPersonActivity() {
        Intent intent = new Intent(context,SearchPersonActivity.class);
        intent.putExtra("role", ModelConfig.ROLE_PUPILS);
        intent.putExtra("target", "select_student");
        startActivityForResult(intent, REQUEST);
    }

    @Override
    protected void clickSecondList(AdapterView<?> parent, View view, int position, long id) {
        student.remove(position);
        studentIdList.remove(position);
        studentAdapter.notifyDataSetChanged();
    }

    @Override
    protected void clickFirstList(AdapterView<?> parent, View view, int position, long id) {
        if(!studentIdList.contains(list.get(position).getId())){
            student.add(list.get(position).getRealName());
            studentIdList.add(list.get(position).getId());
            studentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void loadData() {
        getClassList();
        teacherHomeworkModel = (TeacherHomeworkModel)getIntent().getSerializableExtra("model");
        if(teacherHomeworkModel == null){
            T.showShort(context, "model is null");
            finish();
        }
        group = new ArrayList<String>();
        group.add("");
        student = new ArrayList<String>();
        studentIdList = new ArrayList<>();
    }

    @Override
    protected void showView() {
        super.showView();
        setCanBack();
        postDialog = initPostDialog();
        groupAdapter = new CommonAdapter<String>(this,group,R.layout.item_listview) {
            @Override
            public void convert(ViewHolder viewHolder, String s, int position) {
                viewHolder.getTextView(R.id.item_center).setText(s);
            }
        };
        lvFirstList.setAdapter(groupAdapter);
        if(group.size() == 0){
            lvFirstList.setVisibility(View.GONE);
        }
        studentAdapter = new CommonAdapter<String>(this,student,R.layout.item_person) {
            @Override
            public void convert(ViewHolder viewHolder, String s, int position) {
                viewHolder.getTextView(R.id.tv_person_name).setText(s);
            }
        };
        lvSecondList.setAdapter(studentAdapter);
        if(student.size() == 0){
            emptyLayoutSecond.showEmpty();
        }
    }

    @Override
    protected String initSecondText() {
        return "已选学生";
    }

    @Override
    protected String initFirstText() {
        return "班级学生";
    }

    private void startQuestionActivity() {
//        Question question = new Question();
//        Intent intent = new Intent(SelectStudentActivity.this, QuestionActivity.class);
//        intent.putExtra("question",question);
//        intent.putExtra("state", QuestionFragment.STATE_CHECK);
//        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST && resultCode == RESULT_OK){
            L.i("name: " + data.getStringExtra("name"));
            student.add(data.getStringExtra("name"));
            studentIdList.add(data.getStringExtra("id"));
            studentAdapter.notifyDataSetChanged();
        }
    }

    private PostDialog initPostDialog() {
        PostDialog baseDialog = new PostDialog.Builder(context,R.layout.dialog_post)

                .setTitle("发布作业").
                        setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppAction action = new AppActionImpl(context);
                                SubmitStudentReq submitStudentReq = new SubmitStudentReq();
                                submitStudentReq.setId(teacherHomeworkModel.getHomeworkId());
                                submitStudentReq.setStudentIds(studentIdList);
                                action.submitStudent(submitStudentReq, new ActionCallbackListener<SubmitStudentResp>() {
                                    @Override
                                    public void onSuccess(SubmitStudentResp data) {
                                        if (data.getResult() == SubmitStudentResp.RESULT_SUCCESS) {
                                            T.showShort(context, "发布成功");
                                            finish();
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
                        })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        postDialog.dismiss();
                    }
                }).setHomeWorkName(teacherHomeworkModel.getHomeworkName())
                .setCount(teacherHomeworkModel.getQuestionList().size()+"")
                .setPoint(getStudent(teacherHomeworkModel.getTestSitesList()))
                .getDialog();
        return baseDialog;
    }

    private String getStudent(List<String> student) {
        StringBuffer sb = new StringBuffer();
        Boolean first = true;
        for(String s:student){
            if(first){
                first = false;
            }else{
                sb.append(",");
            }
            sb.append(s);
        }
        return sb.toString();
    }

    public void getClassList() {
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
                    lvFirstList.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
}
