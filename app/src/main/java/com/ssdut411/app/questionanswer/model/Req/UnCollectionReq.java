package com.ssdut411.app.questionanswer.model.Req;

/**
 * Created by LENOVO on 2015/12/26.
 */
public class UnCollectionReq extends BaseReq {
    private String psId;
    private String qId;
    private String collectionId;

    public UnCollectionReq() {
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

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }
}
