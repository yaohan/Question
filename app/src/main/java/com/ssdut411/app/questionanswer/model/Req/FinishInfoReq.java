package com.ssdut411.app.questionanswer.model.Req;

import com.ssdut411.app.questionanswer.model.model.UserModel;

/**
 * Created by yao_han on 2015/12/23.
 */
public class FinishInfoReq extends BaseReq{
    private UserModel userModel;

    public FinishInfoReq() {
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
