package com.ssdut411.app.questionanswer.model.Resp;


/**
 * Created by yao_han on 2015/12/23.
 */
public class LoginResp extends BaseResp {


    public static String DESC_SUCCESS = "登录成功";
    public static String DESC_ERROR = "手机号或密码错误";
    private String id;
    private int type;

    public LoginResp() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
