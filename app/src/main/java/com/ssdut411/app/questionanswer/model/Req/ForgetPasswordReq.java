package com.ssdut411.app.questionanswer.model.Req;

/**
 * Created by yao_han on 2015/12/23.
 */
public class ForgetPasswordReq extends BaseReq{
    private String userName;
    private String password;
    private String checkCode;

    public ForgetPasswordReq() {
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }
}
