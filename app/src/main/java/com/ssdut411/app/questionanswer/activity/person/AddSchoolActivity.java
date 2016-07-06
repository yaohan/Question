package com.ssdut411.app.questionanswer.activity.person;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.system.BaseActivity;
import com.ssdut411.app.questionanswer.core.ActionCallbackListener;
import com.ssdut411.app.questionanswer.core.AppAction;
import com.ssdut411.app.questionanswer.core.AppActionImpl;
import com.ssdut411.app.questionanswer.model.Req.AddClazzReq;
import com.ssdut411.app.questionanswer.model.Req.AddSchoolReq;
import com.ssdut411.app.questionanswer.model.Resp.AddClazzResp;
import com.ssdut411.app.questionanswer.model.Resp.AddSchoolResp;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.utils.T;

/**
 * Created by yao_han on 2016/3/23.
 */
public class AddSchoolActivity extends BaseActivity {
    private LinearLayout llSchool,llClass;
    private EditText tvProvince,tvCity,tvArea,tvSchool,tvGrade,tvClass;
    private String target,schoolId;
    @Override
    protected String initTitle() {
        if(target.equals("school")){
            return "新增学校";
        }else{
            return "新增班级";
        }
    }

    @Override
    protected int initMenu() {
        return R.menu.menu_sure;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_add_school;
    }

    @Override
    protected void initViews() {
        llSchool = getLinearLayout(R.id.ll_school);
        llClass = getLinearLayout(R.id.ll_class);
        tvProvince = getEditText(R.id.et_add_province);
        tvCity = getEditText(R.id.et_add_city);
        tvArea = getEditText(R.id.et_add_area);
        tvSchool = getEditText(R.id.et_add_school);
        tvGrade = getEditText(R.id.et_add_grade);
        tvClass = getEditText(R.id.et_add_class);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.actions_sure:
                        submit();
                }
                return false;
            }

        });
    }
    private void submit() {
        AppAction action = new AppActionImpl(context);
        if(target.equals("school")){
            if(checkSchool()){
                AddSchoolReq addSchoolReq = new AddSchoolReq();
                addSchoolReq.setProvince(tvProvince.getText().toString());
                addSchoolReq.setArea(tvArea.getText().toString());
                addSchoolReq.setCity(tvCity.getText().toString());
                addSchoolReq.setSchool(tvSchool.getText().toString());
                action.addSchool(addSchoolReq, new ActionCallbackListener<AddSchoolResp>() {
                    @Override
                    public void onSuccess(AddSchoolResp data) {
                        T.showShort(context,data.getDesc());
                        if(data.getResult() == AddSchoolResp.RESULT_SUCCESS){
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        T.showShort(context,getString(R.string.error_message));
                    }
                });
            }
        }else{
            if(checkClass()){
                AddClazzReq addClazzReq = new AddClazzReq();
                addClazzReq.setGrade(tvGrade.getText().toString());
                addClazzReq.setClazz(tvClass.getText().toString());
                addClazzReq.setSchoolId(schoolId);
                action.addClazz(addClazzReq, new ActionCallbackListener<AddClazzResp>() {
                    @Override
                    public void onSuccess(AddClazzResp data) {
                        T.showShort(context,data.getDesc());
                        if(data.getResult() == AddClazzResp.RESULT_SUCCESS){
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        T.showShort(context,getString(R.string.error_message));
                    }
                });
            }
        }
    }

    private boolean checkClass() {
        if(checkText(tvGrade)){
        T.showShort(context,"年级输入有误");
        return false;
    }else if(checkText(tvClass)){
        T.showShort(context,"班级输入有误");
        return false;
    }else{
        return true;
    }
    }

    private boolean checkSchool() {
        if(checkText(tvProvince)){
            T.showShort(context,"省份输入有误");
            return false;
        }else if(checkText(tvCity)){
            T.showShort(context,"城市输入有误");
            return false;
        }else if(checkText(tvArea)){
            T.showShort(context,"城区输入有误");
            return false;
        }else if(checkText(tvSchool)){
            T.showShort(context,"学校输入有误");
            return false;
        }else{
            return true;
        }
    }

    private boolean checkText(TextView textView) {
        if(textView == null || textView.getText().length() == 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void showView() {
        setCanBack();
        Intent intent = getIntent();
        target = intent.getStringExtra("target");
        L.i("target:"+target);
        if(target.equals("school")){
            setTitle("新增学校");
            String province = intent.getStringExtra("province");
            if(province != null && province.length()>0){
                tvProvince.setText(province);
                String city = intent.getStringExtra("city");
                if(city !=null && city.length()>0){
                    tvCity.setText(city);
                    String area = intent.getStringExtra("area");
                    if(province != null && province.length()>0){
                        tvArea.setText(area);
                    }
                }
            }
        }else{
            L.i("target:"+target);
            llSchool.setVisibility(View.GONE);
            schoolId = intent.getStringExtra("schoolId");
            String grade = intent.getStringExtra("grade");
            if(grade != null && grade.length()>0){
                tvGrade.setText(grade);
            }
        }
    }
}
