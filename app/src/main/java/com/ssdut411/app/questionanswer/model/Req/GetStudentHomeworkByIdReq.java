package com.ssdut411.app.questionanswer.model.Req;

/**
 * Created by yao_han on 2016/4/16.
 */
public class GetStudentHomeworkByIdReq extends BaseReq {
    private String id;

    public GetStudentHomeworkByIdReq() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
