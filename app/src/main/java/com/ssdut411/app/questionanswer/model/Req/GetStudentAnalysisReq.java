package com.ssdut411.app.questionanswer.model.Req;

/**
 * Created by yao_han on 2016/5/25.
 */
public class GetStudentAnalysisReq extends BaseReq{
    private String userId;
    public GetStudentAnalysisReq() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
