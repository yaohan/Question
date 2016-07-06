package com.ssdut411.app.questionanswer.model.Req;

/**
 * Created by yao_han on 2016/4/13.
 */
public class GetHomeworkByIdReq extends BaseReq {
    private String id;

    public GetHomeworkByIdReq() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
