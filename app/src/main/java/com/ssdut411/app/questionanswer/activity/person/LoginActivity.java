package com.ssdut411.app.questionanswer.activity.person;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.student.MainPageActivity;
import com.ssdut411.app.questionanswer.activity.system.BaseActivity;
import com.ssdut411.app.questionanswer.activity.system.MainActivity;
import com.ssdut411.app.questionanswer.application.MainApplication;
import com.ssdut411.app.questionanswer.core.ActionCallbackListener;
import com.ssdut411.app.questionanswer.core.AppAction;
import com.ssdut411.app.questionanswer.core.AppActionImpl;
import com.ssdut411.app.questionanswer.model.Req.GetInfoReq;
import com.ssdut411.app.questionanswer.model.Req.LoginReq;
import com.ssdut411.app.questionanswer.model.Resp.GetInfoResp;
import com.ssdut411.app.questionanswer.model.Resp.LoginResp;
import com.ssdut411.app.questionanswer.model.model.ModelConfig;
import com.ssdut411.app.questionanswer.model.model.SelfTestModel;
import com.ssdut411.app.questionanswer.model.model.UserModel;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.utils.T;


/**
 * Created by yao_han on 2015/11/28.
 */
public class LoginActivity extends BaseActivity {

    private EditText etCount;
    private EditText etPassword;
    private int type;

    @Override
    protected String initTitle() {
        return "登录";
    }

    @Override
    protected int initMenu() {
        return 0;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        etCount = getEditText(R.id.et_login_count);
        etPassword = getEditText(R.id.et_login_password);

        getImageView(R.id.iv_login_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getTextView(R.id.tv_login_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegisterActivity();
            }
        });
        getButton(R.id.bt_login_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.i("role:" + MainApplication.getInstance().getRole());
                L.i("user:" + (MainApplication.getInstance().getUser() == null));
                    login();
            }
        });
        getTextView(R.id.tv_login_fotget).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startForgetPasswordActivity();
            }
        });

    }

    private void startInfoActivity() {
        Intent intent = new Intent(context, InfoActivity.class);
        startActivity(intent);
        finish();
    }

    private void startRoleActivity() {
        Intent intent = new Intent(context, SelectRoleActivity.class);
        startActivity(intent);
        finish();
    }

    private void startForgetPasswordActivity() {
        T.showShort(context, "forgetPassword");
    }

    private void login() {
        AppAction action = new AppActionImpl(this);
        LoginReq loginReq = new LoginReq();
        loginReq.setPhoneNumber(etCount.getText().toString());
        loginReq.setPassword(etPassword.getText().toString());
        action.login(loginReq, new ActionCallbackListener<LoginResp>() {
            @Override
            public void onSuccess(LoginResp data) {

                if (data.getResult() == LoginResp.RESULT_SUCCESS) {
                    MainApplication.getInstance().setLogin(true);
                    if (!data.getId().equals(MainApplication.getInstance().getUserId())) {
//                        T.showShort(context, "本地有未上传的内容");
                    }
                    MainApplication.getInstance().setUserId(data.getId());
                    type = data.getType();
                    getUserInfo();
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

    private void startMainActivity(int role, UserModel userModel) {
        MainApplication.getInstance().setRole(role);
        Intent intent;
        if (role == ModelConfig.ROLE_NULL) {
            intent = new Intent(context, SelectRoleActivity.class);
            intent.putExtra("target","register");
        } else if (MainApplication.getInstance().getUser() == null) {
            intent = new Intent(context, InfoActivity.class);
        } else {
            if (role == ModelConfig.ROLE_PUPILS) {
                intent = new Intent(context, MainPageActivity.class);
            } else if (role == ModelConfig.ROLE_TEACHER) {
                intent = new Intent(context, com.ssdut411.app.questionanswer.activity.teacher.MainPageActivity.class);
            } else if (role == ModelConfig.ROLE_PARENT) {
                L.i("parent");
                intent = new Intent(context, com.ssdut411.app.questionanswer.activity.parent.MainPageActivity.class);
            } else {
                T.showShort(context, "角色异常");
                intent = new Intent(context, MainActivity.class);
            }
            intent.putExtra("model",userModel);
        }
        startActivity(intent);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void showView() {

    }

    private void startRegisterActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void getUserInfo() {
        AppAction action = new AppActionImpl(context);
        GetInfoReq getInfoReq = new GetInfoReq();
        getInfoReq.setId(MainApplication.getInstance().getUserId());
        action.getInfo(getInfoReq, new ActionCallbackListener<GetInfoResp>() {
            @Override
            public void onSuccess(GetInfoResp data) {
                if(data.getResult() == GetInfoResp.RESULT_SUCCESS){
                    MainApplication.getInstance().setUser(data.getUserModel());
                    startMainActivity(type,data.getUserModel());
                    finish();
                }else{
                    T.showShort(context,data.getDesc());
                }
            }

            @Override
            public void onFailure(String message) {
                T.showShort(context,message);
            }
        });
    }
}
