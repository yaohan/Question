package com.ssdut411.app.questionanswer.model.Req;

/**
 * Created by yao_han on 2016/5/26.
 */
public class GetHeadReq extends BaseReq {
    private String userId;

    public GetHeadReq() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
