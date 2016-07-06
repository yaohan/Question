package com.ssdut411.app.questionanswer.model.Resp;

import java.util.List;

/**
 * Created by LENOVO on 2015/12/25.
 */
public class GetTestSitesResp extends BaseResp {
    public static String DESC_SUCCESS = "获取成功";

    private List<String> testSites;
    public GetTestSitesResp() {
    }

    public List<String> getTestSites() {
        return testSites;
    }

    public void setTestSites(List<String> testSites) {
        this.testSites = testSites;
    }
}
