package com.ssdut411.app.questionanswer.model.Resp;

/**
 * Created by yao_han on 2015/12/23.
 */
public class BaseResp {

    public static int RESULT_SUCCESS = 0;
    public static int RESULT_FAILED = -1;

    private int result;
    private String desc;

    public BaseResp() {
    }

    public int getResult() {
        return result;
    }

    public String getDesc() {
        return desc;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
