package com.ssdut411.app.questionanswer.model.Resp;

/**
 * Created by yao_han on 2015/12/23.
 */
public class RegisterResp extends BaseResp {
    public static String DESC_SUCCESS = "注册成功";
    public static String DESC_HAS_REGISTER = "该手机号已经注册过了";

    private String psId;
    public RegisterResp() {
    }

    public String getPsId() {
        return psId;
    }

    public void setPsId(String psId) {
        this.psId = psId;
    }
}
