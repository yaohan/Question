package com.ssdut411.app.questionanswer.model.Req;

/**
 * Created by LENOVO on 2016/1/12.
 */
public class GetErrorsReq extends BaseReq {
    private String id;

    public GetErrorsReq() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
