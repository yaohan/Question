package com.ssdut411.app.questionanswer.model.Req;

/**
 * Created by LENOVO on 2015/12/26.
 */
public class GetMyTestReq extends BaseReq {
    private String psId;

    public GetMyTestReq() {
    }

    public String getPsId() {
        return psId;
    }

    public void setPsId(String psId) {
        this.psId = psId;
    }
}
