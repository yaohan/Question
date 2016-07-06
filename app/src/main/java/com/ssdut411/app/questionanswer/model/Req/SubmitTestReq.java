package com.ssdut411.app.questionanswer.model.Req;

import com.ssdut411.app.questionanswer.model.model.SelfTestModel;

import java.util.List;

/**
 * Created by LENOVO on 2015/12/26.
 */
public class SubmitTestReq extends BaseReq {

    private SelfTestModel selfTestModel;
    public SubmitTestReq() {
    }

    public SelfTestModel getSelfTestModel() {
        return selfTestModel;
    }

    public void setSelfTestModel(SelfTestModel selfTestModel) {
        this.selfTestModel = selfTestModel;
    }

}
