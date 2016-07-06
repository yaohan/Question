package com.ssdut411.app.questionanswer.model.Resp;

/**
 * Created by yao_han on 2016/5/26.
 */
public class GetHeadResp extends BaseResp {
    public static String DESC_SUCCESS = "获取成功";
    private String head;

    public GetHeadResp() {
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
