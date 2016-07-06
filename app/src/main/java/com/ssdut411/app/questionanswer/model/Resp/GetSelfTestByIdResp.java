package com.ssdut411.app.questionanswer.model.Resp;

import com.ssdut411.app.questionanswer.model.model.SelfTestModel;

/**
 * Created by LENOVO on 2016/1/4.
 */
public class GetSelfTestByIdResp extends BaseResp{
    public static String DESC_SUCCESS = "获取成功";
    private SelfTestModel selfTestModel;

    public GetSelfTestByIdResp() {
    }

    public SelfTestModel getSelfTestModel() {
        return selfTestModel;
    }

    public void setSelfTestModel(SelfTestModel selfTestModel) {
        this.selfTestModel = selfTestModel;
    }
}
