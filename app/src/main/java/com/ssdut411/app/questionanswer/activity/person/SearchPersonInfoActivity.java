package com.ssdut411.app.questionanswer.activity.person;

import android.content.Intent;
import android.view.View;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.model.Resp.LoginResp;
import com.ssdut411.app.questionanswer.model.model.ModelConfig;
import com.ssdut411.app.questionanswer.model.model.UserModel;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.utils.T;

/**
 * Created by yao_han on 2016/4/7.
 */
public class SearchPersonInfoActivity extends ShowInfoActivity {
    int role;
    @Override
    protected int initMenu() {
        return 0;
    }

    @Override
    protected void initViews() {
        super.initViews();
        getTextView(R.id.tv_set_role).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.i("onclick");
                Intent intent = getIntent();
                intent.putExtra("set", true);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    @Override
    protected void loadData() {
        userModel = (UserModel)getIntent().getSerializableExtra("userModel");
        if(userModel !=null){
            getInfo = false;
            L.i("hide menu");
//            hideMenu();
            setView();
            role = getIntent().getIntExtra("role",-1);
            L.i("role：" + role);
            getTextView(R.id.tv_set_role).setVisibility(View.VISIBLE);

            String target = getIntent().getStringExtra("target");
            if(target!= null && target.equals("select_student")){
                getTextView(R.id.tv_set_role).setText("选择学生");
            }else{
                if(role == ModelConfig.ROLE_PUPILS){
                    getTextView(R.id.tv_set_role).setText("设为孩子");
                }else if(role == ModelConfig.ROLE_PARENT){
                    getTextView(R.id.tv_set_role).setText("设为家长");
                }else{
                    T.showShort(context, "role error");
                    getTextView(R.id.tv_set_role).setVisibility(View.GONE);
                }
            }

        }else{
            T.showShort(context, "model is null");
        }
    }

    @Override
    protected void showView() {
        setCanBack();
        setTitle(getString(R.string.person_info));
    }
}
