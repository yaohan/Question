package com.ssdut411.app.questionanswer.model.Resp;

import com.ssdut411.app.questionanswer.model.model.SelfTestModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LENOVO on 2015/12/26.
 */
public class GetNewTestResp extends BaseResp implements Serializable{
    public static String DESC_NOT_ENOUGH = "题库中没有这么多题目";
    public static String DESC_SUCCESS = "获取成功";

    public static int RIGHT_NULL = -1;

    private SelfTestModel selfTestModel;
    
    public GetNewTestResp() {
    }

    public SelfTestModel getSelfTestModel() {
        return selfTestModel;
    }

    public void setSelfTestModel(SelfTestModel selfTestModel) {
        this.selfTestModel = selfTestModel;
    }
}
