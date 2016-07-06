package com.ssdut411.app.questionanswer.model.Resp;

import com.ssdut411.app.questionanswer.model.model.UserModel;

/**
 * Created by yao_han on 2016/3/24.
 */
public class GetInfoResp extends BaseResp{
    private UserModel userModel;

    public GetInfoResp() {
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
