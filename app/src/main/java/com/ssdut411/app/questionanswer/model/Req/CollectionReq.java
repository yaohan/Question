package com.ssdut411.app.questionanswer.model.Req;

/**
 * Created by LENOVO on 2015/12/26.
 */
public class CollectionReq extends BaseReq {
    private String psId;
    private String qId;

    public CollectionReq() {
    }

    public String getPsId() {
        return psId;
    }

    public void setPsId(String psId) {
        this.psId = psId;
    }

    public String getqId() {
        return qId;
    }

    public void setqId(String qId) {
        this.qId = qId;
    }
}
