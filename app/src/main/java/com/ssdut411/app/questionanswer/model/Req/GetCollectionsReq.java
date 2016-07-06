package com.ssdut411.app.questionanswer.model.Req;

/**
 * Created by LENOVO on 2016/3/8.
 */
public class GetCollectionsReq extends BaseReq {
    private String id;
    public GetCollectionsReq() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
