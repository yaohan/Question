package com.ssdut411.app.questionanswer.model.Req;

/**
 * Created by LENOVO on 2016/1/4.
 */
public class GetSelfTestByIdReq extends BaseReq{
    private String id;

    public GetSelfTestByIdReq() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
