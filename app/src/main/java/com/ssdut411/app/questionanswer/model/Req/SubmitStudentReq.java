package com.ssdut411.app.questionanswer.model.Req;

import java.util.List;

/**
 * Created by yao_han on 2016/4/16.
 */
public class SubmitStudentReq extends BaseReq {
    private String id;
    private List<String> studentIds;

    public SubmitStudentReq() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<String> studentIds) {
        this.studentIds = studentIds;
    }
}
