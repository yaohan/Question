package com.ssdut411.app.questionanswer.activity.person;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.system.BaseActivity;
import com.ssdut411.app.questionanswer.application.MainApplication;
import com.ssdut411.app.questionanswer.core.ActionCallbackListener;
import com.ssdut411.app.questionanswer.core.AppAction;
import com.ssdut411.app.questionanswer.core.AppActionImpl;
import com.ssdut411.app.questionanswer.model.Req.GetInfoReq;
import com.ssdut411.app.questionanswer.model.Resp.GetInfoResp;
import com.ssdut411.app.questionanswer.model.Resp.LoginResp;
import com.ssdut411.app.questionanswer.model.model.UserModel;
import com.ssdut411.app.questionanswer.utils.BitmapUtils;
import com.ssdut411.app.questionanswer.utils.GsonUtils;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.utils.T;
import com.ssdut411.app.questionanswer.widget.EmptyLayout;

/**
 * Created by yao_han on 2016/4/5.
 */
public class ShowInfoActivity extends BaseActivity {
    protected UserModel userModel;
    protected boolean getInfo = true;
    private EmptyLayout emptyLayout;

    @Override
    protected void onResume() {
        super.onResume();
        if(getInfo){
            getInfoFromNet();
        }
    }

    @Override
    protected String initTitle() {
        return "个人信息";
    }

    @Override
    protected int initMenu() {
        return R.menu.menu_edit;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_show_info;
    }

    @Override
    protected void initViews() {
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.actions_edit:
                        Intent intent = new Intent(context, InfoActivity.class);
                        intent.putExtra("role", MainApplication.getInstance().getRole());
                        startActivity(intent);
                }
                return true;
            }
        });
//        getLinearLayout(R.id.ll_class).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context,ClassDetailActivity.class);
//                L.i("userModel:"+ GsonUtils.gsonToJsonString(userModel));
//                intent.putExtra("schoolId",userModel.getSchoolId());
//                intent.putExtra("classId",userModel.getClassId());
//                startActivity(intent);
//            }
//        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void showView() {
        setCanBack();
        getTextView(R.id.tv_set_role).setVisibility(View.GONE);
    }

    public void setView() {
        getTextView(R.id.tv_info_name).setText(userModel.getRealName());
        String head = userModel.getHeadPortrait();
        BitmapUtils.showHeadFromNet(context,getImageView(R.id.iv_info_head),userModel.getId());
        String gender = userModel.getGender();
        if(gender.equals("male")){
            getTextView(R.id.tv_info_gender).setText("男");
        }else{
            getTextView(R.id.tv_info_gender).setText("女");
        }
        getTextView(R.id.tv_info_age).setText(userModel.getAge() + "岁");
        if(userModel.getSchoolName() == null && userModel.getClassName() == null){
            getRelativeLayout(R.id.rl_school).setVisibility(View.GONE);
        }else{
            getTextView(R.id.tv_info_school).setText(userModel.getSchoolName());
        }
        if(userModel.getClassName() ==null){
            getRelativeLayout(R.id.rl_class).setVisibility(View.GONE);
        }else{
            getTextView(R.id.tv_info_class).setText(userModel.getClassName());
        }
        if(userModel.getParentName() ==null){
            getRelativeLayout(R.id.rl_info_parent).setVisibility(View.GONE);
        }else{
            getTextView(R.id.tv_info_parent).setText(userModel.getParentName());
        }
        if(userModel.getChildName() == null){
            getRelativeLayout(R.id.rl_info_child).setVisibility(View.GONE);
        }else{
            getTextView(R.id.tv_info_child).setText(userModel.getChildName());
        }
        if(userModel.getSubjectName() == null){
            getRelativeLayout(R.id.rl_info_subject).setVisibility(View.GONE);
        }else{
            getTextView(R.id.tv_info_subject).setText(userModel.getSubjectName());
        }
        getTextView(R.id.tv_info_contact).setText(userModel.getContact());
        getTextView(R.id.tv_info_address).setText(userModel.getAddress());
    }

    public void getInfoFromNet() {
        AppAction action = new AppActionImpl(context);
        GetInfoReq getInfoReq = new GetInfoReq();
        getInfoReq.setId(MainApplication.getInstance().getUserId());
        action.getInfo(getInfoReq, new ActionCallbackListener<GetInfoResp>() {
            @Override
            public void onSuccess(GetInfoResp data) {
                if (data.getResult() == GetInfoResp.RESULT_SUCCESS) {
                    userModel = data.getUserModel();
                    setView();
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
}
