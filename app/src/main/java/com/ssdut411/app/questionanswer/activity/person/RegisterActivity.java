package com.ssdut411.app.questionanswer.activity.person;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.system.BaseActivity;
import com.ssdut411.app.questionanswer.application.MainApplication;
import com.ssdut411.app.questionanswer.core.ActionCallbackListener;
import com.ssdut411.app.questionanswer.core.AppAction;
import com.ssdut411.app.questionanswer.core.AppActionImpl;
import com.ssdut411.app.questionanswer.model.Req.LoginReq;
import com.ssdut411.app.questionanswer.model.Req.RegisterReq;
import com.ssdut411.app.questionanswer.model.Resp.RegisterResp;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.utils.T;


/**
 * Created by yao_han on 2015/11/28.
 */
public class RegisterActivity extends BaseActivity {


    private EditText etCount;
    private EditText etCheck;
    private EditText etPassword;
    private EditText etPassword2;

    @Override
    protected String initTitle() {
        return "注册";
    }

    @Override
    protected int initMenu() {
        return R.menu.menu_null;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViews() {
        etCount = getEditText(R.id.et_register_count);
        etCheck = getEditText(R.id.et_register_check);
        etPassword = getEditText(R.id.et_register_password);
        etPassword2 = getEditText(R.id.et_register_password2);
        getImageView(R.id.iv_register_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        if(check()){
            AppAction action = new AppActionImpl(this);
            RegisterReq registerReq = new RegisterReq();
            registerReq.setPhoneNumber(etCount.getText().toString());
            registerReq.setPassword(etPassword.getText().toString());
            registerReq.setCheckCode(etCheck.getText().toString());
            action.register(registerReq, new ActionCallbackListener<RegisterResp>() {
                @Override
                public void onSuccess(RegisterResp data) {
                    if (data.getResult() == RegisterResp.RESULT_SUCCESS) {
                        MainApplication.getInstance().setUserId(data.getPsId());
                        startSelectRoleActivity();
                    }
                        T.showShort(context, data.getDesc());
                }

                @Override
                public void onFailure(String message) {
                    T.showShort(context, getString(R.string.error_message));
                }
            });
        }
    }

    private void startSelectRoleActivity() {
        Intent intent = new Intent(context, SelectRoleActivity.class);
        intent.putExtra("target","register");
        startActivity(intent);
    }

    private boolean check() {
        if(etCount.getText().toString().equals(R.string.empty)){
            T.showShort(context,"手机号不能为空");
            return false;
        }else if(etPassword.getText().toString().equals(getString(R.string.empty))){
            T.showShort(context,"密码不能为空");
            return false;
        }else if(!etPassword.getText().toString().equals(etPassword2.getText().toString())){
            T.showShort(context,"两次密码不同");
            return false;
        }else if(etCheck.getText().toString().equals(getString(R.string.empty))){
            T.showShort(context,"验证码不能为空，随便输一个");
            return false;
        }
        return true;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void showView() {
        setCanBack();
    }
}
