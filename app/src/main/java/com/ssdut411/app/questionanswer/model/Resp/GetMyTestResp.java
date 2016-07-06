package com.ssdut411.app.questionanswer.model.Resp;



import com.ssdut411.app.questionanswer.model.model.TestHistory;

import java.util.Date;
import java.util.List;

/**
 * Created by LENOVO on 2015/12/26.
 */
public class GetMyTestResp extends BaseResp {
    public static String DESC_SUCCESS = "获取成功";

    private List<TestHistory> testInfoList;

    public GetMyTestResp() {
    }

    public List<TestHistory> getTestInfoList() {
        return testInfoList;
    }

    public void setTestInfoList(List<TestHistory> testInfoList) {
        this.testInfoList = testInfoList;
    }

}
